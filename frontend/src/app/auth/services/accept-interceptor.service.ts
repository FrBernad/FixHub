import {Injectable} from '@angular/core';
import {HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';

import {TranslateService} from "@ngx-translate/core";

@Injectable()
export class AcceptInterceptorService implements HttpInterceptor {

  constructor(
    // private translateService: TranslateService,
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    // console.log(this.translateService.currentLang)
    // const modifiedReq = req.clone({
      // headers: new HttpHeaders().set('Accept', this.translateService.currentLang)
    // });
    return next.handle(req);
  }
}
