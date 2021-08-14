import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse, HttpStatusCode,} from '@angular/common/http';
import {Subject} from 'rxjs';
import {JobPaginationQuery, JobPaginationResult} from "../../../../discover/discover.service";
import {environment} from "../../../../../environments/environment";
import {Job} from "../../../../models/job.model";
import * as Url from "url";
import {Router} from "@angular/router";


@Injectable({providedIn: 'root'})
export class WorksService {

  results = new Subject<JobPaginationResult>();

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {
  }

  getUserJobs(jp: JobPaginationQuery) {
    this.http
      .get<Job[]>(
        environment.apiBaseUrl + '/user/jobs',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...jp}})
        },
      ).subscribe((res) => {
        if (res.status === HttpStatusCode.NoContent) {
          this.results.next({
            totalPages: 0,
            results: []
          });
        } else {
          const jr: JobPaginationResult = this.parsePaginationResult(res);
          this.results.next(jr);
        }
      },
      () => {
        this.router.navigate(['500'])
      });
  }

  private parsePaginationResult(res: HttpResponse<Job[]>): JobPaginationResult {

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
