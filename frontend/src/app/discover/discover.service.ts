import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse, HttpStatusCode,} from '@angular/common/http';
import {Subject} from 'rxjs';
import {environment} from '../../environments/environment';
import {Job} from "../models/job.model";
import {DefaultUrlSerializer, Router, UrlSerializer} from "@angular/router";
import * as Url from "url";

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
      () => {
        this.router.navigate(['500'])
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

    const totalPages: number = Number(new HttpParams({fromString: Url.parse(lastLink).query})
      .get("page")[0]) + 1;

    return {
      totalPages,
      results: res.body
    }
  }


}
