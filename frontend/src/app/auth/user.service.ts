import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Subject} from 'rxjs';
import {User} from "../models/user.model";
import {environment} from "../../environments/environment";
import {tap} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class UserService {
  user = new BehaviorSubject<User>(null);
  loading = new BehaviorSubject<boolean>(true);

  constructor(private http: HttpClient) {}

  populateUserData() {
    return this.http
      .get<User>(
        environment.apiBaseUrl + '/user'
      ).pipe(tap(
        res => {
          console.log(res)
          this.user.next(res);
        }
      ))
  }

  setLoading(value: boolean) {
    this.loading.next(value);
  }

  clearUser() {
    this.user.next(null);
  }

  updateProfileInfo(profileInfo) {
    return this.http.put(
      environment.apiBaseUrl + '/user',
      {
        ...this.user.getValue(),
        ...profileInfo
     })
      .subscribe(()=> this.user.next({...this.user.getValue(),...profileInfo}));

  }


  uploadProfileImage() {

  }

  uploadCoverImage() {

  }

}
