import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {environment} from "../../../environments/environment";
import {HTTP_INTERCEPTORS, HttpStatusCode} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {RouterTestingModule} from "@angular/router/testing";
import {BehaviorSubject} from "rxjs";
import {AuthInterceptorService} from "./auth-interceptor.service";
import {JobService} from "../../job/job.service";
import {Session} from "../../models/session.model";
import {TranslateService} from "@ngx-translate/core";

describe('AuthService', () => {
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let authInterceptorService: AuthInterceptorService;
  let authService: AuthService;
  let jobService: JobService;
  let translateService: TranslateService;
  const jwt = "X-JWT";
  const refreshToken = "X-Refresh-Token";

  class TranslateServiceStub {
    currentLang: String = "es";
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule]
      , providers:
        [
          AuthService,
          AuthInterceptorService,
          {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptorService,
            multi: true
          },
          JobService,
          {provide: TranslateService, useClass: TranslateServiceStub}
        ],
    });

    injector = getTestBed();
    httpMock = injector.inject(HttpTestingController);
    authService = injector.inject(AuthService);
    jobService = injector.inject(JobService);
    translateService = injector.inject(TranslateService);
    authInterceptorService = injector.inject(AuthInterceptorService);
  });

  it('intercept() should add auth header and lang', () => {
    authService.session = new BehaviorSubject<Session>(new Session(jwt, new Date(Date.now() + 1000000), refreshToken));
    jobService.createJob(new FormData()).subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs');
    expect(req.request.headers.has('Authorization')).toEqual(true);
    expect(req.request.headers.get('Accept-Language')).toEqual("es");
    req.flush({}, {
      status: HttpStatusCode.Ok,
      statusText: HttpStatusCode.Ok.toString()
    });
  });

  afterEach(() => {
    httpMock.verify();
  });

});
