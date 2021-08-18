import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams, HttpResponse, HttpStatusCode,} from '@angular/common/http';
import {Subject} from 'rxjs';
import {environment} from '../../environments/environment';
import {Job} from "../models/job.model";
import {Router} from "@angular/router";

export interface JobPaginationQuery {
  query?: string;
  order?: string;
  category?: string;
  state?: string;
  city?: string;
  page: number;
  pageSize?: number;
}

export interface JobPaginationResult {
  totalPages: number;
  results: Job[];
}

export interface State {
  id: number;
  name: string;
}

export interface City {
  id: number;
  name: string;
}


@Injectable({providedIn: 'root'})
export class DiscoverService {

  results = new Subject<JobPaginationResult>();

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {
  }

  getCategories() {
    return this.http
      .get<{ values: string[] }>(
        environment.apiBaseUrl + '/jobs/categories',
      )
  }

  getStates() {
    return this.http
      .get<State[]>(
        environment.apiBaseUrl + '/locations/states'
      )
  }

  getStateCities(id: string) {
    return this.http
      .get<City[]>(
        environment.apiBaseUrl + '/locations/state/' + id + '/cities'
      )
  }

  getJobs(jp: JobPaginationQuery) {
    this.http
      .get<Job[]>(
        environment.apiBaseUrl + '/jobs',
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
      (error: HttpErrorResponse) => {
        if (error.status === HttpStatusCode.NotFound) {
          this.router.navigate(['404']);
        } else {
          this.router.navigate(['500']);
        }
      }
    )
    ;
  }

  private parsePaginationResult(res: HttpResponse<Job[]>): JobPaginationResult {

    const lastLink: string = res.headers
      .getAll('Link')
      .pop()
      .split(',')
      .filter((link) => (link.includes("last")))
      .pop()
      .match(/<(.*)>/)[1];

    const totalPages: number = Number(new URL(lastLink).searchParams.get("page")) + 1;

    return {
      totalPages,
      results: res.body
    }
  }


}
