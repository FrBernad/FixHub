import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {User} from "../../models/user.model";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";
import {City, State} from "../../discover/discover.service";


export interface ProviderInfo {
  schedule: {
    startTime: string;
    endTime: string
  };
  location: {
    cities: City[];
    state: State
  }
}

@Injectable({providedIn: 'root'})
export class UserService {
  user = new BehaviorSubject<User>(null);
  loading = new BehaviorSubject<boolean>(true);

  constructor(
    private http: HttpClient,
  ) {
  }

  getUser(id: number) {
    return this.http
      .get<User>(
        environment.apiBaseUrl + '/users/' + id
      );
  }

  populateUserData() {
    return this.http
      .get<User>(
        environment.apiBaseUrl + '/user'
      ).pipe(tap(
        res => {
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
      }).pipe(tap(() => this.user.next({...this.user.getValue(), ...profileInfo})));
  }

  follow(id: number) {
    return this.http.put(
      environment.apiBaseUrl + '/user/following/' + id, {}).pipe(tap(() => {
      this.populateUserData().subscribe()
    }));
  }

  unfollow(id: number) {
    return this.http.delete(
      environment.apiBaseUrl + '/user/following/' + id, {}).pipe(tap(() => {
      this.populateUserData().subscribe()
    }));
  }

  updateProfileImage(profileImage: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/user/profileImage',
      profileImage).pipe(tap(() => {
      this.populateUserData().subscribe();
    }));
  }

  updateCoverImage(coverImage: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/user/coverImage',
      coverImage).pipe(tap(() => {
      this.populateUserData().subscribe();
    }));
  }

  updateProviderInfo(providerInfo: ProviderInfo) {
    return this.http.put(
      environment.apiBaseUrl + '/user/account/provider',
      providerInfo)
      .pipe(tap(() => {
        this.populateUserData().subscribe();
      }));
  }


}