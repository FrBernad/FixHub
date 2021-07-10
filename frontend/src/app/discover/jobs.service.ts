import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';
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

  populateJobs(pageSize: number) {
    this.getJobs({
      page: 0,
      pageSize
    })
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
      this.results.next(res.body);
    });
  }


}
