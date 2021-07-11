import {Injectable} from '@angular/core';
import {
  HttpClient,
  HttpStatusCode,
  HttpParams,
} from '@angular/common/http';
import {throwError, BehaviorSubject, Subject} from 'rxjs';
import {environment} from '../../environments/environment';
import {Job} from "../models/job.model";

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
  page: number;
  totalPages: number;
  results: Job[];
  query?: string;
  order?: string;
  category?: string;
  state?: string;
  city?: string;
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
export class JobsService {

  results = new Subject<JobPaginationResult>();

  constructor(
    private http: HttpClient,
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
      .get<JobPaginationResult>(
        environment.apiBaseUrl + '/jobs',
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
