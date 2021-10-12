import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, concatMap, mergeMap, shareReplay, tap} from 'rxjs/operators';
import {BehaviorSubject, Observable, of, throwError} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Session} from '../../models/session.model';
import jwtDecode from "jwt-decode";
import {ProviderInfo, UserService} from "./user.service";
import {NotificationsService} from "../../user/notifications/notifications.service";

interface Jwt {
  exp: number,
  iat: number,
  roles: string,
  id: number,
  sub: string,
}

export interface RegisterData {
  email: string,
  password: string,
  name: string,
  surname: string,
  phoneNumber: string,
  state: string,
  city: string
}

@Injectable({providedIn: 'root'})
export class AuthService {

  session = new BehaviorSubject<Session>(null);

  autoLogin: Observable<boolean> =
    of(true)
      .pipe(
        concatMap((_) => {
          this.handleAutoLogin();
          return this.http
            .post(
              environment.apiBaseUrl + '/user/refreshToken',
              {},
              {
                observe: "response"
              }
            )
            .pipe(
              catchError(() => {
                  this.userService.setLoading(false);
                  return of(true);
                }
              ),
              concatMap((res: HttpResponse<Object>) => {
                  if (res as unknown as boolean === true) {
                    return of(true);
                  }
                  this.handleSession(res);
                  return this.userService.populateUserData()
                    .pipe(
                      catchError(() => {
                          this.userService.setLoading(false);
                          return of(true);
                        }
                      ),
                      concatMap(() => {
                          this.notificationsService.initNotificationsInterval();
                          this.userService.setLoading(false);
                          return of(true);
                        }
                      )
                    )
                }
              ),
            )
        }),
        shareReplay(1)
      );

  // private tokenExpirationTimer: any;

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private notificationsService: NotificationsService,
    private router: Router
  ) {
  }

  signup(registerData: RegisterData) {
    return this.http
      .post<Session>(
        environment.apiBaseUrl + '/users',
        {
          ...registerData
        },
        {
          observe: "response"
        }
      )
  }

  login(email: string, password: string) {
    return this.http
      .post(
        environment.apiBaseUrl + '/user',
        {},
        {
          headers: new HttpHeaders().set('Authorization', "Basic " + btoa(email + ":" + password)),
          observe: "response",
        }
      )
      .pipe(
        tap(res => {
          this.handleLogin(res);
        })
      );
  }

  verify(token: string) {
    return this.http
      .put(
        environment.apiBaseUrl + '/users/emailVerification',
        {
          token: token,
        },
        {
          observe: "response"
        }
      )
      .pipe(
        tap(res => {
            this.handleSession(res);
          }
        )
      );
  }

  resendVerificationEmail() {
    return this.http
      .post(
        environment.apiBaseUrl + '/users/emailVerification',
        {},
        {
          observe: "response"
        }
      );
  }

  makeProvider(providerInfo: ProviderInfo) {
    return this.http.post(
      environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/provider',
      providerInfo,
      {
        observe: "response"
      }
    ).pipe(
      tap((res) => {
          this.handleSession(res);
        }
      ),
      mergeMap(() => {
        return this.userService.populateUserData();
      })
    );
  }

  handleAutoLogin() {
    const refreshToken = localStorage.getItem("refresh-token");

    if (refreshToken) {
      this.session.next(new Session(null, null, refreshToken));
    }
  }

  handleLogin(res: HttpResponse<Object>) {
    const refreshToken = res.headers.get("X-Refresh-Token");
    localStorage.setItem("refresh-token", refreshToken);
    this.handleSession(res);
  }

  handleSession(res: HttpResponse<Object>) {
    const authHeader = res.headers.get("X-JWT");
    const token = authHeader.split(" ")[1];
    this.handleAuthentication(token);
  }

  resetPassword(token: string, password: string) {
    return this.http
      .put(
        environment.apiBaseUrl + '/users/passwordReset',
        {
          token,
          password
        },
        {
          observe: "response"
        }
      )
  }

  sendResetPasswordEmail(email: string) {
    return this.http
      .post(
        environment.apiBaseUrl + '/users/passwordReset',
        {
          email,
        },
        {
          observe: "response"
        }
      );
  }

  logout() {
    // this.http
    // .delete(
    //   environment.apiBaseUrl + '/user/refreshToken',
    //   {observe: "response"},
    // ).subscribe();

    this.session.next(null);

    localStorage.removeItem("refresh-token");

    this.router.navigate(['/login']).then(() => {
      this.userService.clearUser();
    });

    this.notificationsService.clearNotificationsInterval();

    // if (this.tokenExpirationTimer) {
    //   clearTimeout(this.tokenExpirationTimer);
    // }
    // this.tokenExpirationTimer = null;
  }

  // autoRefreshToken(expirationDuration: number) {
  //   if (this.tokenExpirationTimer) {
  //     clearTimeout(this.tokenExpirationTimer);
  //   }
  //
  //   this.tokenExpirationTimer = setTimeout(() => {
  //     this.http.post(
  //       environment.apiBaseUrl + '/user/refreshToken',
  //       {},
  //       {
  //         observe: "response"
  //       }
  //     ).pipe(
  //       catchError(() => {
  //           this.logout();
  //           return throwError("");
  //         }
  //       ),
  //       mergeMap((res: HttpResponse<Object>) => {
  //         this.handleSession(res);
  //         return this.userService.populateUserData()
  //       })
  //     ).subscribe();
  //   }, expirationDuration);
  //
  // }

  private handleAuthentication(token: string) {
    const jwt = this.decodeToken(token);

    const milliExpirationTime = (jwt.exp - jwt.iat) * 1000;
    const refreshToken = localStorage.getItem("refresh-token")
    const expirationDate = new Date(new Date().getTime() + milliExpirationTime);
    // this.autoRefreshToken(milliExpirationTime);

    const newSession = new Session(token, expirationDate, refreshToken);
    this.userService.setRoles(jwt.roles.split(" "));
    this.session.next(newSession);
  }

  private decodeToken(token: string): Jwt {
    return jwtDecode(token);
  }

}
