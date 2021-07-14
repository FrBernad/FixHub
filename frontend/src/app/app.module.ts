import {APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, Inject, isDevMode, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {APP_BASE_HREF} from "@angular/common";
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import {AppRoutingModule} from './app-routing.module';

import {NewJobComponent} from './job/new-job/new-job.component';
import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {DiscoverComponent} from './discover/discover.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {JobComponent} from './job/job.component';
import {LandingPageLayoutComponent} from './layouts/landing-page-layout/landing-page-layout.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {DefaultLayoutComponent} from './layouts/default-layout/default-layout.component';
import {ContactComponent} from './job/contact/contact.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RecoverEmailPopupComponent} from './login/recover-email-popup/recover-email-popup.component';
import {AuthComponent} from './auth/auth.component';
import {ReviewFormComponent} from './job/review-form/review-form.component';
import {PopularJobCardComponent} from './landing-page/popular-job-card/popular-job-card.component';
import {JobCardComponent} from './discover/job-card/job-card.component';
import {EditJobComponent} from './job/edit-job/edit-job.component';
import {ErrorsComponent} from './errors/errors.component';
import {JoinComponent} from './join/join.component';
import {ChooseCityComponent} from './join/choose-city/choose-city.component';
import {ChooseStateComponent} from './join/choose-state/choose-state.component';
import {FooterComponent} from './footer/footer.component';
import {AuthInterceptorService} from "./auth/auth-interceptor.service";
import {UpdateInfoComponent} from './profile/session-profile/update-info/update-info.component';
import {PaginationComponent} from './pagination/pagination.component';
import {UpdateProviderInfoComponent} from './update-provider-info/update-provider-info.component';
import {LoadingScreenComponent} from './loading-screen/loading-screen.component';
import {ReviewCardComponent} from './job/review-card/review-card.component';
import {ReviewPaginationComponent} from './job/review-pagination/review-pagination.component';
import {RequestJobCardComponent} from './dashboard/request/request-job-card/request-job-card.component';
import {FollowersComponent} from './followers/followers.component';
import {FollowerCardComponent} from './followers/follower-card/follower-card.component';
import {FollowingComponent} from "./followers/following.component";
import {UserProfileComponent} from './profile/user-profile/user-profile.component';
import {SessionProfileComponent} from "./profile/session-profile/session-profile.component";
import {LoadingSpinnerComponent} from './loading-spinner/loading-spinner.component';
import {WorksComponent} from './dashboard/works/works.component';
import {VerifyComponent} from './verify/verify.component';
import {ResetPasswordComponent} from './reset-password/reset-password.component';
import {UserSentRequestsComponent} from "./profile/session-profile/contact/user-sent-requests.component";
import {UserSentRequestCardComponent} from './profile/session-profile/contact/user-sent-request-card/user-sent-request-card.component';
import {RequestComponent} from './dashboard/request/request.component';
import {AuthService} from "./auth/auth.service";
import {Observable} from "rxjs";


@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [
    AppComponent,
    NavbarComponent,
    LandingPageComponent,
    DiscoverComponent,
    SessionProfileComponent,
    DashboardComponent,
    LoginComponent,
    RegisterComponent,
    JobComponent,
    LandingPageLayoutComponent,
    DefaultLayoutComponent,
    ContactComponent,
    RecoverEmailPopupComponent,
    ReviewFormComponent,
    PopularJobCardComponent,
    JobCardComponent,
    AuthComponent,
    EditJobComponent,
    NewJobComponent,
    ErrorsComponent,
    JoinComponent,
    ChooseCityComponent,
    ChooseStateComponent,
    FooterComponent,
    UpdateInfoComponent,
    PaginationComponent,
    UpdateProviderInfoComponent,
    LoadingScreenComponent,
    ReviewCardComponent,
    ReviewPaginationComponent,
    RequestJobCardComponent,
    FollowersComponent,
    FollowingComponent,
    FollowerCardComponent,
    LoadingSpinnerComponent,
    UserProfileComponent,
    WorksComponent,
    VerifyComponent,
    ResetPasswordComponent,
    UserSentRequestsComponent,
    UserSentRequestCardComponent,
    RequestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: translateFactory,
        deps: [HttpClient]
      }
    }),
    FontAwesomeModule,
    ReactiveFormsModule,
    NgxMaterialTimepickerModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    },
    {
      provide: APP_BASE_HREF,
      useValue: '/'
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}

export function translateFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient, isDevMode() ? "/assets/i18n/" : "/resources/assets/i18n/");
}
