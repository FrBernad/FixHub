import {Injectable} from '@angular/core';
import {HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {exhaustMap, take} from 'rxjs/operators';

import {AuthService} from './auth.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return this.authService.session.pipe(
      take(1),
      exhaustMap(token => {
        if (!token) {
          return next.handle(req);
        }
        const modifiedReq = req.clone({
          headers: new HttpHeaders().set('Authorization', "Bearer "+token.token)
        });
        return next.handle(modifiedReq);
      })
    );
  }
}
