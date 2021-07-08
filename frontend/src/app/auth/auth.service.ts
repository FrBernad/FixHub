import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';
import {throwError, BehaviorSubject} from 'rxjs';
import {environment} from '../../environments/environment';
import {Session} from '../models/session.model';
import jwtDecode from "jwt-decode";

interface jwt {
  exp: number,
  iat: number,
  sub: string,
}

@Injectable({providedIn: 'root'})
export class AuthService {
  session = new BehaviorSubject<Session>(null);
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {
  }

  signup(email: string, password: string) {
    return this.http
      .post<Session>(
        environment.apiBaseUrl + 'signUp',
        {
          email: email,
          password: password,
          returnSecureToken: true
        }
      )
      .pipe(
        catchError(this.handleError),
        tap(resData => {
          // this.handleAuthentication(
          //   resData.email,
          //   resData.idToken,
          //   +resData.expiresIn
          // );
        })
      );
  }

  login(email: string, password: string) {
    return this.http
      .post(
        environment.apiBaseUrl + '/user',
        {
          email: email,
          password: password,
          returnSecureToken: true
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
    const userData: {
      email: string;
      id: string;
      _token: string;
      _tokenExpirationDate: string;
    } = JSON.parse(localStorage.getItem('userData'));
    if (!userData) {
      return;
    }
    //
    // const loadedUser = new UserModel(
    //   userData.email,
    //   userData.id,
    //   userData._token,
    //   new Date(userData._tokenExpirationDate)
    // );

    this.session.next(null);
    const expirationDuration =
      new Date(userData._tokenExpirationDate).getTime() -
      new Date().getTime();
    this.autoLogout(expirationDuration);
  }

  logout() {
    this.session.next(null);
    this.router.navigate(['/auth']);
    localStorage.removeItem('userData');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  private handleAuthentication(
    token: string,
  ) {
    const jwt = this.decodeToken(token);
    const expirationDate = new Date(new Date().getTime() + jwt.exp);
    this.autoLogout(jwt.exp);
    const newSession = new Session(token, expirationDate);
    this.session.next(newSession);
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

  private decodeToken(token: string): jwt {
    return jwtDecode(token);
  }

}
