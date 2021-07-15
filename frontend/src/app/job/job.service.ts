import {HttpClient, HttpParams, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {Review} from "./review.model";
import {SingleJob} from "../models/single-job.model";

export interface JobData {
    jobProvided: string,
    jobCategory: string,
    price: number,
    description: string,
    paused?: boolean
}

export interface ReviewsPaginationResult {
  page: number;
  totalPages: number;
  results: Review[];
}

export interface ReviewsPaginationQuery {
  page: number;
  pageSize?: number;
}

@Injectable({providedIn: 'root'})
export class JobService {

  rpr: ReviewsPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  reviews = new Subject<ReviewsPaginationResult>();
  firstReviews = new Subject<ReviewsPaginationResult>();

  constructor(
    private http: HttpClient
  ) {
  }

  getReviews(rpq: ReviewsPaginationQuery, id: number) {
    this.http
      .get<ReviewsPaginationResult>(
        environment.apiBaseUrl + '/jobs/' + id + '/reviews',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...rpq}})
        },
      ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.reviews.next({
          page: 0,
          totalPages: 0,
          results: []
        });
      } else {
        this.reviews.next(res.body);
      }
    });
  }

  getFirstReviews(id: number) {
    this.http
      .get<ReviewsPaginationResult>(
        environment.apiBaseUrl + '/jobs/' + id + '/reviews',
        {
          observe: "response",
          params: new HttpParams({fromObject: {page: 0, pageSize: 4}})
        },
      ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.firstReviews.next({
          page: 0,
          totalPages: 0,
          results: []
        });
      } else {
        this.firstReviews.next(res.body);
      }
    });
  }

  updateReviews(id:number){
    this.getFirstReviews(id);
  }

  createJob(formData: FormData) {
    return this.http.post<FormData>(environment.apiBaseUrl + '/jobs', formData, {observe: 'response'});
  }

  updateJob(id:number, formData: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/jobs/' + id ,formData);
  }

  getJob(id: number) {
    return this.http.get<SingleJob>(environment.apiBaseUrl + '/jobs/' + id);
  }

  createReview(review: { description: string, rating: string }, id: number) {
    return this.http.post<{
      description: string, rating: string
    }>(environment.apiBaseUrl + '/jobs/' + id + '/reviews', review);
  }


}
