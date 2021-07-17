import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NgModule} from "@angular/core";
import {AppRoutingModule} from "./app-routing.module";
import {AppTranslateModule} from "./app-translate.module";
import {FontAwesomeTestingModule} from "@fortawesome/angular-fontawesome/testing";
import {ReactiveFormsModule} from "@angular/forms";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  providers: [],
  exports: [
    HttpClientTestingModule,
    BrowserAnimationsModule,
    HttpClientTestingModule,
    AppRoutingModule,
    AppTranslateModule,
    FontAwesomeTestingModule,
    ReactiveFormsModule,
    NgxMaterialTimepickerModule,
  ],
})
export class TestingModule {
}
