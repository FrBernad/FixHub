import {NgModule} from "@angular/core";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {environment} from "../environments/environment";

@NgModule({
  imports: [TranslateModule.forRoot({
    defaultLanguage: 'en',
    loader: {
      provide: TranslateLoader,
      useFactory: translateFactory,
      deps: [HttpClient]
    }
  })],
  exports: [TranslateModule],
})

export class AppTranslateModule {
}

export function translateFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient, environment.i18nBase);
}
