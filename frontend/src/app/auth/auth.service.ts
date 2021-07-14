import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';
import {throwError, BehaviorSubject} from 'rxjs';
import {environment} from '../../environments/environment';
import {Session} from '../models/session.model';
import jwtDecode from "jwt-decode";
import {UserService} from "./user.service";

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
      .pipe(
        catchError(this.handleError),
      );
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
        catchError(this.handleError),
        tap(res => {
          const authHeader = res.headers.get("Authorization");
          const token = authHeader.split(" ")[1];
          this.handleAuthentication(token);
        })
      );
  }

  autoLogin() {
    const sessionData: {
      _token: string;
      _tokenExpirationDate: string;
    } = JSON.parse(localStorage.getItem('session'));
    if (!sessionData) {
      this.userService.setLoading(false);
      return;
    }
    const session = new Session(sessionData._token, new Date(sessionData._tokenExpirationDate));
    if (session.token) {
      this.session.next(session);
      this.userService.populateUserData().subscribe(() => {
        const expirationDuration =
          new Date(sessionData._tokenExpirationDate).getTime() -
          new Date().getTime();
        this.autoLogout(expirationDuration);
        this.userService.setLoading(false);
      }, () => {
        this.userService.setLoading(false);
      })
    } else {
      this.userService.setLoading(false);
      localStorage.removeItem("session")
    }

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
        catchError(this.handleError),
        tap(res => {
          const authHeader = res.headers.get("Authorization");
          const token = authHeader.split(" ")[1];
          this.handleAuthentication(token);
        })
      );
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
      .pipe(
        catchError(this.handleError),
      );
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
    this.session.next(null);

    this.userService.clearUser();

    this.router.navigate(['/login']);
    localStorage.removeItem('session');
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

  private handleAuthentication(
    token: string,
  ) {
    const jwt = this.decodeToken(token);

    const milliExpirationTime = (jwt.exp - jwt.iat) * 1000;

    const expirationDate = new Date(new Date().getTime() + milliExpirationTime);
    this.autoLogout(milliExpirationTime);

    const newSession = new Session(token, expirationDate);
    this.session.next(newSession);

    localStorage.setItem('session', JSON.stringify(newSession));
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (!errorRes.error || !errorRes.error.error) {
      return throwError(errorMessage);
    }
    switch (errorRes.error.error.message) {
      case 'EMAIL_EXISTS':
        errorMessage = 'This email exists already';
        break;
      case 'EMAIL_NOT_FOUND':
        errorMessage = 'This email does not exist.';
        break;
      case 'INVALID_PASSWORD':
        errorMessage = 'This password is not correct.';
        break;
    }
    return throwError(errorMessage);
  }

  private decodeToken(token: string): Jwt {
    return jwtDecode(token);
  }

}
