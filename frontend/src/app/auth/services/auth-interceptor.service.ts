import {Injectable} from '@angular/core';
import {
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
  HttpStatusCode
} from '@angular/common/http';
import {exhaustMap, map, take} from 'rxjs/operators';

import {AuthService} from './auth.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return this.authService.session.pipe(
      take(1),
      exhaustMap(token => {
        if (!token) {
          return next.handle(req);
        }
        const modifiedReq = req.clone({
          headers: new HttpHeaders().set('Authorization', "Bearer " + token.token)
        });
        return next.handle(modifiedReq)
          .pipe(
            map(resp => {
              if (resp instanceof HttpResponse) {
                if(resp.status === HttpStatusCode.Unauthorized){
                  this.authService.logout();
                }
                else if(resp.headers.has("X-JWT")){
                  this.authService.handleSession(resp);
                }
                return resp;
              }
            })
          );
      })
    );
  }
}
