import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {environment} from "../../../environments/environment";
import {HttpHeaders, HttpStatusCode} from "@angular/common/http";
import {ProviderInfo, UserService} from "./user.service";
import {AuthService, RegisterData} from "./auth.service";
import {NotificationsService} from "../../user/notifications/notifications.service";
import {RouterTestingModule} from "@angular/router/testing";
import {of} from "rxjs";

describe('AuthService', () => {
  let injector: TestBed;
  let authService: AuthService;
  let userService: UserService;
  let httpMock: HttpTestingController;
  const jwtHeaderVal = "Bearer JWT";
  const refreshTokenHeaderVal = "Bearer REFRESH_TOKEN";
  const jwtHeader = "X-JWT";
  const refreshTokenHeader = "X-Refresh-Token";
  const jwt = {
    exp: 10000,
    iat: 10000,
    roles: 'VERIFIED',
    userUrl: 'http://host/users/1',
    sub: 'USER'
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,
        RouterTestingModule.withRoutes(
          [{path: 'login', redirectTo: ''}]
        )
      ],
      providers: [AuthService, UserService, NotificationsService]
    });
    injector = getTestBed();
    authService = injector.inject(AuthService);
    userService = injector.inject(UserService);
    httpMock = injector.inject(HttpTestingController);
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1})
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

  it('verify() should return new jwt', () => {
    authService.verify("VERIFICATION_TOKEN").subscribe(
      (res) => {
        expect(res.status).toEqual(HttpStatusCode.NoContent);
        expect(res.headers.get(refreshTokenHeader)).toEqual(refreshTokenHeaderVal);
        expect(res.headers.get(jwtHeader)).toEqual(jwtHeaderVal);
      }
    )

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/emailVerification');
    expect(req.request.method).toBe('PUT');

    spyOn<any>(authService, 'decodeToken').and.returnValue(jwt)

    req.flush({},
      {
        headers: new HttpHeaders({
          "X-JWT": jwtHeaderVal,
          "X-Refresh-Token": refreshTokenHeaderVal
        }),
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
    spyOn(userService, 'getUserProviderDetails').and.returnValue(of(undefined));
    spyOn<any>(authService, 'decodeToken').and.returnValue(jwt)
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + userService.user.getValue().id + '/provider');
    expect(req.request.method).toBe('POST');

    req.flush({},
      {
        headers: new HttpHeaders({
          "X-JWT": jwtHeaderVal,
          "X-Refresh-Token": refreshTokenHeaderVal
        }),
      });
  });

  it('resetPassword() should return no content', () => {
    authService.resetPassword("PASSWORD_RESET_TOKEN", "new_password")
      .subscribe(
        (res) => {
          expect(res.status).toEqual(HttpStatusCode.NoContent)
        }
      );

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/passwordReset');
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

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/emailVerification');
    expect(req.request.method).toBe('POST');

    req.flush({},
      {
        status: HttpStatusCode.NoContent,
        statusText: HttpStatusCode.Created.toString()
      });
  });


  it('logout() should clear session', () => {

    authService.logout();

    expect(authService.session.getValue()).toBe(null);
  });

  afterEach(() => {
    httpMock.verify();
  });

});
