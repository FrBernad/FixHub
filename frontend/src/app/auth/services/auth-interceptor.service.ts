import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
  HttpStatusCode
} from '@angular/common/http';
import {catchError, exhaustMap, map, take} from 'rxjs/operators';

import {AuthService} from './auth.service';
import {throwError} from "rxjs";
import {TranslateService} from "@ngx-translate/core";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private translateService: TranslateService,
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return this.authService.session.pipe(
      take(1),
      exhaustMap(token => {

        const langReq = req.clone({
          headers: req.headers.set('Accept-Language', this.translateService.currentLang)
        });

        if (!token) {
          return next.handle(langReq);
        }

        const authRequest = langReq.clone({
          headers: langReq.headers.set('Authorization', "Bearer " + token.token)
        });

        return next.handle(authRequest)
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
