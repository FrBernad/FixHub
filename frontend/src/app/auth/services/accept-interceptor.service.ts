import {Injectable, Injector} from '@angular/core';
import {HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';

import {TranslateService} from "@ngx-translate/core";

@Injectable()
export class AcceptInterceptorService implements HttpInterceptor {

  constructor(
    private readonly injector: Injector
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const translateService: TranslateService = this.injector.get(TranslateService)
    console.log(translateService.currentLang)
    const modifiedReq = req.clone({
      headers: new HttpHeaders().set('Accept', translateService.currentLang)
    });
    return next.handle(modifiedReq);
  }
}
