import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, of} from 'rxjs';
import {ProviderDetails, User} from "../../models/user.model";
import {environment} from "../../../environments/environment";
import {concatMap, map, tap} from "rxjs/operators";
import {City, State} from "../../discover/discover.service";
import {ContactInfo} from "../../models/contact-info.model";

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
        environment.apiBaseUrl + '/users/' + this.user.getValue().id
      ).pipe(
        tap(
          res => {
            this.user.next({...this.user.getValue(), ...res});
          }
        ),
        concatMap(res => {
          if (!this.user.getValue().roles.includes('VERIFIED')) {
            return of(res)
          }
          return this.getUserContactInfo()
            .pipe(
              concatMap(_ => {
                if (!this.user.getValue().roles.includes('PROVIDER')) {
                  return of(this.user.getValue())
                }
                return this.getUserProviderDetails(this.user.getValue().id)
                  .pipe(
                    tap(
                      res => {
                        this.user.next({...this.user.getValue(), ...{providerDetails: res}});
                      },
                      map(
                        _ => {
                          return of(this.user.getValue())
                        }
                      )
                    )
                  )
              })
            );
        })
      )
  }

  repopulateUserData() {
    return this.http
      .get<User>(
        environment.apiBaseUrl + '/users/' + this.user.getValue().id
      ).pipe(
        tap(
          res => {
            this.user.next({...this.user.getValue(), ...res});
          }
        )
      );
  }

  getUserContactInfo() {
    return this.http
      .get<ContactInfo[]>(
        environment.apiBaseUrl + '/users/' + this.user.getValue().id + "/contactInfo"
      ).pipe(tap(
        res => {
          this.user.next({...this.user.getValue(), ...{contactInfo: !!res ? res : []}});
        }
      ))
  }

  getUserProviderDetails(id: number) {
    return this.http
      .get<ProviderDetails>(
        environment.apiBaseUrl + '/users/' + id + "/provider"
      )
  }

  repopulateProviderDetails() {
    return this.getUserProviderDetails(this.user.getValue().id).pipe(
      tap(res => this.user.next({...this.user.getValue(), ...{providerDetails: res}})
      )
    )
  }

  setRoles(roles: string[]) {
    this.user.next({
      ...this.user.getValue(),
      roles
    });
  }

  setId(id: number) {
    this.user.next({
      ...this.user.getValue(),
      id
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
      this.repopulateUserData().subscribe((_) => {
      }, (_) => {
      })
    }));
  }

  unfollow(id: number) {
    return this.http.delete(
      environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/following/' + id, {}).pipe(tap(() => {
      this.repopulateUserData().subscribe((_) => {
      }, (_) => {
      })
    }));
  }

  updateProfileImage(profileImage: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/profileImage',
      profileImage).pipe(tap(() => {
      this.repopulateUserData().subscribe((_) => {
      }, (_) => {
      });
    }));
  }

  updateCoverImage(coverImage: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/coverImage',
      coverImage).pipe(tap(() => {
      this.repopulateUserData().subscribe((_) => {
      }, (_) => {
      });
    }));
  }

  updateProviderInfo(providerInfo: ProviderInfo) {
    return this.http.put(
      environment.apiBaseUrl + '/users/' + this.user.getValue().id + '/provider',
      providerInfo)
      .pipe(
        concatMap(
          (_) => this.getUserProviderDetails(this.user.getValue().id)
            .pipe(
              tap(
                res => {
                  this.user.next({...this.user.getValue(), ...{providerDetails: res}});
                }
              )
            )
        )
      );
  }


}
