import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams, HttpStatusCode} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {UserService} from "../../auth/services/user.service";
import {tap} from "rxjs/operators";
import {JobRequest} from "../../models/job-request.model";

export interface ContactPaginationResult {
  page: number;
  totalPages: number;
  results: [];
}


export interface ContactPaginationQuery {
  page: number;
  pageSize?: number;
}

export interface RequestPaginationResult {
  page: number;
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
export class ContactService {

  results = new Subject<ContactPaginationResult>();

  constructor(private http: HttpClient,
              private userService: UserService) {
  }

  newContact(jobId: number, contactData: ContactData) {
    return this.http.post<ContactData>(environment.apiBaseUrl + '/jobs/' + jobId + '/contact', contactData).pipe(tap(() => {
      this.userService.populateUserData().subscribe();
    }));
  }

  getProviderRequests(rq: RequestPaginationQuery) {
    this.http.get<ContactPaginationResult>(
      environment.apiBaseUrl + '/user/jobs/requests',
      {
        observe: "response",
        params: new HttpParams({fromObject: {...rq}})
      },
    ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.results.next({
          page: 0,
          totalPages: 0,
          results: []
        });
      } else {
                //FIXME:
                console.log(res.body)
        this.results.next(res.body);
      }
    });
  }

  getProviderJobRequest(id: number) {
    return this.http.get<JobRequest>(
      environment.apiBaseUrl + '/user/jobs/requests/' + id,
    )
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

  changeContactStatus(contactId: number, status: string) {
    return this.http.put(
      environment.apiBaseUrl + '/user/jobs/requests/' + contactId,
      {
        status
      }
    );
  }

}
