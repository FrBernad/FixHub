import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Subject} from 'rxjs';
import {User} from "../models/user.model";
import {environment} from "../../environments/environment";
import {tap} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class UserService {
  user = new Subject<User>();

  constructor(
    private http: HttpClient,
  ) {
  }

  populateUserData() {
    return this.http
      .get<User>(
        environment.apiBaseUrl + '/user'
      ).pipe(tap(
        newUser => {
          console.log(newUser);
          this.user.next(newUser);
        }
      ))
  }

  clearUser() {
    this.user.next(null);
  }

}
