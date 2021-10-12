import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {User} from "../../models/user.model";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";
import {City, State} from "../../discover/discover.service";

export interface ProfileInfo {
  name: string,
  surname: string,
  phoneNumber: string,
  state: string,
  city: string
}

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
          this.user.next({...this.user.getValue(), ...res});
        }
      ))
  }

  setRoles(roles: string[]) {
    this.user.next({
      ...this.user.getValue(),
      roles
    });
  }

  setLoading(value: boolean) {
    this.loading.next(value);
  }

  clearUser() {
    this.user.next(null);
  }

  isFollowed(followId: number) {
    return this.http.get(
      environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/following/' + followId,
      {observe: "response"})
  }

  updateProfileInfo(profileInfo: ProfileInfo) {
    return this.http.put(
      environment.apiBaseUrl + '/users/' + this.user.getValue().id,
      {
        ...this.user.getValue(),
        ...profileInfo
      }).pipe(tap(() => this.user.next({...this.user.getValue(), ...profileInfo})));
  }

  follow(id: number) {
    return this.http.put(
      environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/following/' + id, {}).pipe(tap(() => {
      this.populateUserData().subscribe()
    }));
  }

  unfollow(id: number) {
    return this.http.delete(
      environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/following/' + id, {}).pipe(tap(() => {
      this.populateUserData().subscribe()
    }));
  }

  updateProfileImage(profileImage: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/profileImage',
      profileImage).pipe(tap(() => {
      this.populateUserData().subscribe();
    }));
  }

  updateCoverImage(coverImage: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/coverImage',
      coverImage).pipe(tap(() => {
      this.populateUserData().subscribe();
    }));
  }

  updateProviderInfo(providerInfo: ProviderInfo) {
    return this.http.put(
      environment.apiBaseUrl + '/users/account/provider',
      providerInfo)
      .pipe(tap(() => {
        this.populateUserData().subscribe();
      }));
  }


}
