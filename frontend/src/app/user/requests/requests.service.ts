import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams, HttpResponse, HttpStatusCode} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {UserService} from "../../auth/services/user.service";
import {map, tap} from "rxjs/operators";
import {JobRequest} from "../../models/job-request.model";
import {Router} from "@angular/router";

export interface RequestPaginationResult {
  totalPages: number;
  results: [];
}

export interface RequestPaginationQuery {
  page: number;
  order?: string
  pageSize?: number;
  status?: string;
}

export interface ContactData {
  state: string;
  city: string;
  street: string;
  floor: string;
  departmentNumber: string;
  message: string;
  contactInfoId: string;
  userId: string;
  addressNumber: string;
}

@Injectable({providedIn: 'root'})
export class RequestsService {

  sentRequests = new Subject<RequestPaginationResult>();
  receivedRequests = new Subject<RequestPaginationResult>();

  constructor(private http: HttpClient,
              private userService: UserService,
              private router: Router
  ) {
  }

  newContact(jobId: number, contactData: ContactData) {
    return this.http.post<ContactData>(
      environment.apiBaseUrl + '/jobs/' + jobId + '/contact',
      contactData,
      {
        observe: "response"
      }
    )
      .pipe(
        tap(() => {
            this.userService.populateUserData().subscribe();
          }
        ),
        map((res) => {
            let location = res.headers.get('location').split('/');
            return location[location.length - 1];
          }
        )
      );
  }

  getProviderRequests(rq: RequestPaginationQuery) {
    this.http.get<[]>(
      environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/receivedRequests',
      {
        observe: "response",
        params: new HttpParams({fromObject: {...rq}})
      },
    ).subscribe((res) => {
        if (res.status === HttpStatusCode.NoContent) {
          this.receivedRequests.next({
            totalPages: 0,
            results: []
          });
        } else {
          const cr: RequestPaginationResult = this.parsePaginationResult(res);
          this.receivedRequests.next(cr);
        }
      },
      () => {
        this.router.navigate(['500'])
      }
    );
  }

  getJobRequest(id: number) {
    return this.http.get<JobRequest>(
      environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/receivedRequests/' + id,
    )
  }

  getUserSentRequests(rp: RequestPaginationQuery) {
    this.http.get<[]>(
      environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/sentRequests',
      {
        observe: "response",
        params: new HttpParams({fromObject: {...rp}})
      },
    ).subscribe((res) => {
        if (res.status === HttpStatusCode.NoContent) {
          this.sentRequests.next({
            totalPages: 0,
            results: []
          });
        } else {
          const cr: RequestPaginationResult = this.parsePaginationResult(res);
          this.sentRequests.next(cr);
        }
      },
      () => {
        this.router.navigate(['500'])
      }
    );
  }

  changeContactStatus(contactId: number, status: string) {
    return this.http.put(
      environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/receivedRequests/' + contactId,
      {
        status
      },
      {
        observe: "response"
      }
    );
  }

  private parsePaginationResult(res: HttpResponse<[]>): RequestPaginationResult {

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
