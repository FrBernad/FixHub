import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {LoadingScreenComponent} from './loading-screen/loading-screen.component';
import {SharedModule} from "./shared/shared.module";
import {CoreModule} from "./core.module";
import {AppTranslateModule} from "./app-translate.module";

@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [AppComponent, LoadingScreenComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    CoreModule,
    SharedModule,
    AppRoutingModule,
    AppTranslateModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}

