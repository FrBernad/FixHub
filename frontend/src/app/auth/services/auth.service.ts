import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, mergeMap, shareReplay, tap} from 'rxjs/operators';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Session} from '../../models/session.model';
import jwtDecode from "jwt-decode";
import {ProviderInfo, UserService} from "./user.service";

interface Jwt {
  exp: number,
  iat: number,
  roles: string,
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

  autoLogin: Observable<boolean> = this.http
    .post(
      environment.apiBaseUrl + '/user/refreshSession',
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
      mergeMap((res: HttpResponse<Object>) => {
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
              mergeMap(() => {
                  this.userService.setLoading(false);
                  return of(true);
                }
              )
            )
        }
      ),
      shareReplay(1)
    );

  private tokenExpirationTimer: any;

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private router: Router
  ) {
  }

  signup(registerData: RegisterData) {
    return this.http
      .post<Session>(
        environment.apiBaseUrl + '/users',
        {
          ...registerData
        }
      )
  }

  login(email: string, password: string) {
    return this.http
      .post(
        environment.apiBaseUrl + '/user',
        {
          email: email,
          password: password,
        },
        {
          observe: "response"
        }
      )
      .pipe(
        tap(res => {
          this.handleSession(res);
        })
      );
  }


  verify(token: string) {
    return this.http
      .put(
        environment.apiBaseUrl + '/user/verify',
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


  makeProvider(providerInfo: ProviderInfo) {
    return this.http.post(
      environment.apiBaseUrl + '/user/account/provider',
      providerInfo)
      .pipe(
        tap((res: HttpResponse<Object>) => {
            this.handleSession(res);
          },
          mergeMap(() => {
            return this.userService.populateUserData();
          })
        )
      );
  }

  handleSession(res: HttpResponse<Object>) {
    const authHeader = res.headers.get("Authorization");
    const token = authHeader.split(" ")[1];
    this.handleAuthentication(token);
  }

  resetPassword(token: string, password: string) {
    return this.http
      .put(
        environment.apiBaseUrl + '/user/resetPassword',
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
        environment.apiBaseUrl + '/user/resetPassword',
        {
          email,
        },
        {
          observe: "response"
        }
      );
  }

  resendVerificationEmail() {
    return this.http
      .post(
        environment.apiBaseUrl + '/user/verify',
        {},
        {
          observe: "response"
        }
      );
  }

  logout() {
    this.http
      .delete(
        environment.apiBaseUrl + '/user/refreshSession',
        {observe: "response"},
      ).subscribe();

    this.session.next(null);

    this.userService.clearUser();
    this.router.navigate(['/login']);

    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  private handleAuthentication(token: string) {
    const jwt = this.decodeToken(token);

    const milliExpirationTime = (jwt.exp - jwt.iat) * 1000;

    const expirationDate = new Date(new Date().getTime() + milliExpirationTime);
    this.autoLogout(milliExpirationTime);

    const newSession = new Session(token, expirationDate);
    this.session.next(newSession);
  }

  private decodeToken(token: string): Jwt {
    return jwtDecode(token);
  }

}
