import {HttpClientTestingModule} from "@angular/common/http/testing";
import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {AppTranslateModule} from "./app-translate.module";
import {CoreModule} from "./core.module";
import {FontAwesomeTestingModule} from "@fortawesome/angular-fontawesome/testing";
import {ReactiveFormsModule} from "@angular/forms";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {BrowserModule} from "@angular/platform-browser";
import {AppComponent} from "./app.component";
import {LoadingScreenComponent} from "./loading-screen/loading-screen.component";

@NgModule({
  declarations: [AppComponent, LoadingScreenComponent],
  providers: [],
  exports: [
    CommonModule,
    HttpClientModule,
    HttpClientTestingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientTestingModule,
    CoreModule,
    AppRoutingModule,
    AppTranslateModule,
    FontAwesomeTestingModule,
    ReactiveFormsModule,
    NgxMaterialTimepickerModule
  ],
})
export class TestingModule {
}
