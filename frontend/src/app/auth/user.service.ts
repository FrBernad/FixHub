import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {User} from "../models/user.model";
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";
import {tap} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class UserService {
  user = new BehaviorSubject<User>(null);

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

}
