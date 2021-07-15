import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpStatusCode,} from '@angular/common/http';
import {Subject} from 'rxjs';
import {JobPaginationQuery, JobPaginationResult} from "../../discover/jobs.service";
import {environment} from "../../../environments/environment";


@Injectable({providedIn: 'root'})
export class WorksService {

  results = new Subject<JobPaginationResult>();

  constructor(
    private http: HttpClient,
  ) {
  }

  getUserJobs(jp: JobPaginationQuery) {
    this.http
      .get<JobPaginationResult>(
        environment.apiBaseUrl + '/user/jobs',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...jp}})
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
