import {HttpClient, HttpParams, HttpResponse, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {User} from "../../models/user.model";
import * as Url from "url";
import {Router} from "@angular/router";

export interface FollowPaginationResult {
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
    private http: HttpClient,
    private router: Router
  ) {
  }

  getFollowers(frq: FollowPaginationQuery, id: number) {
    this.http
      .get<User[]>(
        environment.apiBaseUrl + '/users/' + id + '/followers',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...frq}})
        },
      ).subscribe((res) => {
        if (res.status === HttpStatusCode.NoContent) {
          this.follows.next({
            totalPages: 0,
            results: []
          });
        } else {
          const fr: FollowPaginationResult = this.parsePaginationResult(res);
          this.follows.next(fr);
        }
      },
      () => {
        this.router.navigate(['500'])
      }
    );
  }

  getFollowing(frq: FollowPaginationQuery, id: number) {
    this.http
      .get<User[]>(
        environment.apiBaseUrl + '/users/' + id + '/following',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...frq}})
        },
      ).subscribe((res) => {
        if (res.status === HttpStatusCode.NoContent) {
          this.follows.next({
            totalPages: 0,
            results: []
          });
        } else {
          const fr: FollowPaginationResult = this.parsePaginationResult(res);
          this.follows.next(fr);
        }
      },
      () => {
        this.router.navigate(['500'])
      }
    );
  }

  private parsePaginationResult(res: HttpResponse<User[]>): FollowPaginationResult {

    const lastLink: string = res.headers
      .getAll('Link')
      .pop()
      .split(',')
      .filter((link) => (link.includes("last")))
      .pop()
      .match(/<(.*)>/)[1];

    const totalPages: number = Number(new HttpParams({fromString: Url.parse(lastLink).query})
      .get("page")[0]) + 1;

    return {
      totalPages,
      results: res.body
    }
  }


}
