import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NgModule} from "@angular/core";
import {AppTranslateModule} from "./app-translate.module";
import {FontAwesomeTestingModule} from "@fortawesome/angular-fontawesome/testing";
import {RouterTestingModule} from "@angular/router/testing";

@NgModule({
  providers: [],
  exports: [
    HttpClientTestingModule,
    RouterTestingModule,
    AppTranslateModule,
    FontAwesomeTestingModule,
  ],
})
export class TestingModule {
}
