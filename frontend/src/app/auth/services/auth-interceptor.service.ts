import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
  HttpStatusCode
} from '@angular/common/http';
import {catchError, exhaustMap, map, take} from 'rxjs/operators';

import {AuthService} from './auth.service';
import {throwError} from "rxjs";

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
            catchError((err) => {
                if (err instanceof HttpErrorResponse) {
                  if (err.status === HttpStatusCode.Unauthorized) {
                    this.authService.logout();
                  }
                  return throwError(err);
                }
              }
            ),
            map(resp => {
              if (resp instanceof HttpResponse) {
                if (resp.headers.has("X-JWT")) {
                  this.authService.handleJWT(resp);
                }
                return resp;
              }
            })
          );
      })
    );
  }
}
