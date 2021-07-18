import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {environment} from "../../../environments/environment";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {RouterTestingModule} from "@angular/router/testing";
import {BehaviorSubject} from "rxjs";
import {AuthInterceptorService} from "./auth-interceptor.service";
import {JobService} from "../../job/job.service";
import {Session} from "../../models/session.model";

describe('AuthService', () => {
  let injector: TestBed;
  let httpMock: HttpTestingController;
  let authInterceptorService: AuthInterceptorService;
  let authService: AuthService;
  let jobService: JobService;
  let jwt = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6IlZFUklGSUVEIFBST1ZJREVSIFVTRVIiLCJzdWIiOiJjb2NvQHlvcG1haWwuY29tIiwiaWF0IjoxNjI2NDk3MjAyLCJleHAiOjE2MjY0OTg0MDJ9.vCyFCn2H9yTlAd_1NEeQWKO1-6oyf635E0feRWb-SLw";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers:
        [
          AuthService,
          AuthInterceptorService,
          {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptorService,
            multi: true
          },
          JobService
        ],
    });

    injector = getTestBed();
    httpMock = injector.inject(HttpTestingController);
    authService = injector.inject(AuthService);
    jobService = injector.inject(JobService);
    authInterceptorService = injector.inject(AuthInterceptorService);
  });

  it('intercept() should add auth header', () => {
    authService.session = new BehaviorSubject<Session>(new Session(jwt, new Date(Date.now() + 1000000)));
    jobService.createJob(new FormData()).subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs');
    expect(req.request.headers.has('Authorization')).toEqual(true);
  });

});
