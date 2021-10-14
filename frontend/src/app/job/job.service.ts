import {HttpClient, HttpParams, HttpResponse, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {Review} from "./review.model";
import {SingleJob} from "../models/single-job.model";
import {concatMap, map} from "rxjs/operators";
import {UserService} from "../auth/services/user.service";

export interface JobData {
  jobProvided: string,
  jobCategory: string,
  price: number,
  description: string,
  paused?: boolean
}

export interface ReviewsPaginationResult {
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
    totalPages: 0,
  }

  reviews = new Subject<ReviewsPaginationResult>();
  firstReviews = new Subject<ReviewsPaginationResult>();

  constructor(
    private http: HttpClient,
    private userService: UserService,
  ) {
  }

  getReviews(rpq: ReviewsPaginationQuery, id: number) {
    this.http
      .get<Review[]>(
        environment.apiBaseUrl + '/jobs/' + id + '/reviews',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...rpq}})
        },
      ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.reviews.next({
          totalPages: 0,
          results: []
        });
      } else {
        const rr: ReviewsPaginationResult = this.parsePaginationResult(res);
        this.reviews.next(rr);
      }
    });
  }

  getFirstReviews(id: number) {
    this.http
      .get<Review[]>(
        environment.apiBaseUrl + '/jobs/' + id + '/reviews',
        {
          observe: "response",
          params: new HttpParams({fromObject: {page: 0, pageSize: 4}})
        },
      ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.firstReviews.next({
          totalPages: 0,
          results: []
        });
      } else {
        const rr: ReviewsPaginationResult = this.parsePaginationResult(res);
        this.firstReviews.next(rr);
      }
    });
  }

  updateReviews(id: number) {
    this.getFirstReviews(id);
  }

  createJob(formData: FormData) {
    return this.http.post<FormData>(environment.apiBaseUrl + '/jobs', formData, {observe: 'response'});
  }

  updateJob(id: number, formData: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/jobs/' + id, formData, {observe: 'response'});
  }

  getJob(id: number) {
    let currentJob: SingleJob;

    return this.http.get<SingleJob>(environment.apiBaseUrl + '/jobs/' + id)
      .pipe(
        concatMap(
          job => {
            currentJob = job;
            return this.userService.getUserProviderDetails(job.provider.id)
              .pipe(
                map(info => {
                  currentJob.provider.providerDetails = info;
                  return currentJob;
                })
              )
          }
        )
      );
  }

  createReview(review: { description: string, rating: string }, id: number) {
    return this.http.post<{
      description: string, rating: string
    }>(environment.apiBaseUrl + '/jobs/' + id + '/reviews', review, {observe: 'response'});
  }


  private parsePaginationResult(res: HttpResponse<Review[]>): ReviewsPaginationResult {

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
