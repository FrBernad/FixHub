import {NgModule} from "@angular/core";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {AuthInterceptorService} from "./auth/services/auth-interceptor.service";
import {APP_BASE_HREF} from "@angular/common";
import {AcceptInterceptorService} from "./auth/services/accept-interceptor.service";

@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    },
    // {
    //   provide: HTTP_INTERCEPTORS,
    //   useClass: AcceptInterceptorService,
    //   multi: true
    // },
    {
      provide: APP_BASE_HREF,
      useValue: '/'
    }
  ]
})
export class CoreModule {
}
