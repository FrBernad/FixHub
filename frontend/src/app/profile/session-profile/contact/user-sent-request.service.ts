import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {HttpClient, HttpParams, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {ContactPaginationQuery, ContactPaginationResult} from "./user-sent-request.component";

@Injectable({providedIn: 'root'})
export class UserSentRequestService {

  results = new Subject<ContactPaginationResult>();

  constructor(
    private http: HttpClient,
  ) {
  }

  getUserSentRequests(cp: ContactPaginationQuery) {
    this.http.get<ContactPaginationResult>(
      environment.apiBaseUrl + '/user/jobs/sentRequests',
      {
        observe: "response",
        params: new HttpParams({fromObject: {...cp}})
      },
    ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.results.next({
          page: 0,
          totalPages: 0,
          results: []
        });
      } else {
        this.results.next(res.body);
      }
    });

  }


}
