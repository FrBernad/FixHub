import {HttpClient, HttpParams, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {User} from "../../models/user.model";

export interface FollowPaginationResult {
  page: number;
  totalPages: number;
  results: User[];
}

export interface FollowPaginationQuery {
  page: number;
  pageSize?: number;
}

@Injectable({providedIn: 'root'})
export class FollowService {

  follows = new Subject<FollowPaginationResult>();

  constructor(
    private http: HttpClient
  ) {
  }

  getFollowers(frq: FollowPaginationQuery, id: number) {
    this.http
      .get<FollowPaginationResult>(
        environment.apiBaseUrl + '/users/' + id + '/followers',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...frq}})
        },
      ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.follows.next({
          page: 0,
          totalPages: 0,
          results: []
        });
      } else {
        this.follows.next(res.body);
      }
    });
  }

  getFollowing(frq: FollowPaginationQuery, id: number) {
    this.http
      .get<FollowPaginationResult>(
        environment.apiBaseUrl + '/users/' + id + '/following',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...frq}})
        },
      ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.follows.next({
          page: 0,
          totalPages: 0,
          results: []
        });
      } else {
        this.follows.next(res.body);
      }
    });
  }


}
