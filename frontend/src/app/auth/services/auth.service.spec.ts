import {TestBed, getTestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {environment} from "../../../environments/environment";
import {HttpHeaders, HttpStatusCode} from "@angular/common/http";
import {ProviderInfo, UserService} from "./user.service";
import {AuthService, RegisterData} from "./auth.service";
import {NotificationsService} from "../../user/notifications/notifications.service";
import {Router} from "@angular/router";
import {RouterTestingModule} from "@angular/router/testing";
import {of} from "rxjs";

describe('AuthService', () => {
  let injector: TestBed;
  let authService: AuthService;
  let userService: UserService;
  let httpMock: HttpTestingController;
  let authHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6IlZFUklGSUVEIFBST1ZJREVSIFVTRVIiLCJzdWIiOiJjb2NvQHlvcG1haWwuY29tIiwiaWF0IjoxNjI2NDk3MjAyLCJleHAiOjE2MjY0OTg0MDJ9.vCyFCn2H9yTlAd_1NEeQWKO1-6oyf635E0feRWb-SLw";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [AuthService, UserService, NotificationsService],
    });

    injector = getTestBed();
    authService = injector.inject(AuthService);
    userService = injector.inject(UserService);
    httpMock = injector.inject(HttpTestingController);
  });


  it('signup() should return created', () => {
    let userData: RegisterData = {
      email: "email",
      password: "password",
      name: "name",
      surname: "surname",
      phoneNumber: "+5491136411487",
      state: "state",
      city: "city"
    }

    authService.signup(userData).subscribe(
      (res) => {
        expect(res.status).toEqual(HttpStatusCode.Created)
      }
    )

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users');
    expect(req.request.method).toBe('POST');

    req.flush(userData,
      {
        status: HttpStatusCode.Created,
        statusText: HttpStatusCode.Created.toString()
      });

  });

  it('login() should return new jwt', () => {
    authService.login("email", "password").subscribe(
      (res) => {
        expect(res.status).toEqual(HttpStatusCode.NoContent);
        expect(res.headers.get("Authorization")).toEqual(authHeader);
      }
    )

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user');
    expect(req.request.method).toBe('POST');

    req.flush({},
      {
        headers: new HttpHeaders({Authorization: authHeader}),
        status: HttpStatusCode.NoContent,
        statusText: HttpStatusCode.Created.toString()
      });
  });

  it('verify() should return new jwt', () => {
    authService.verify("VERIFICATION_TOKEN").subscribe(
      (res) => {
        expect(res.status).toEqual(HttpStatusCode.NoContent);
        expect(res.headers.get("Authorization")).toEqual(authHeader);
      }
    )

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/verify');
    expect(req.request.method).toBe('PUT');

    req.flush({},
      {
        headers: new HttpHeaders({Authorization: authHeader}),
        status: HttpStatusCode.NoContent,
        statusText: HttpStatusCode.Created.toString()
      });
  });

  it('makeProvider() should return new jwt', () => {
    let providerInfo: ProviderInfo = {
      schedule: undefined,
      location: undefined
    }

    authService.makeProvider(providerInfo).subscribe();
    spyOn(userService, 'populateUserData').and.returnValue(of(undefined));

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/account/provider');
    expect(req.request.method).toBe('POST');

    req.flush({},
      {
        headers: new HttpHeaders({Authorization: authHeader}),
      });
  });

  it('resetPassword() should return no content', () => {
    authService.resetPassword("PASSWORD_RESET_TOKEN", "new_password")
      .subscribe(
        (res) => {
          expect(res.status).toEqual(HttpStatusCode.NoContent)
        }
      );

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/resetPassword');
    expect(req.request.method).toBe('PUT');

    req.flush({},
      {
        status: HttpStatusCode.NoContent,
        statusText: HttpStatusCode.Created.toString()
      });
  });


  it('resendVerificationEmail() should return no content', () => {

    authService.resendVerificationEmail()
      .subscribe(
        (res) => {
          expect(res.status).toEqual(HttpStatusCode.NoContent)
        }
      );

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/verify');
    expect(req.request.method).toBe('POST');

    req.flush({},
      {
        status: HttpStatusCode.NoContent,
        statusText: HttpStatusCode.Created.toString()
      });
  });


  it('logout() should return no content', () => {

    authService.logout();

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/refreshSession');
    expect(req.request.method).toBe('DELETE');

    req.flush({});
  });

  afterEach(() => {
    httpMock.verify();
  });

});
