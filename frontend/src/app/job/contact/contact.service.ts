import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams, HttpStatusCode} from '@angular/common/http';
import {ContactInfo} from 'src/app/models/contact-info.model';
import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {User} from "../../models/user.model";
import {UserService} from "../../auth/user.service";
import {tap} from "rxjs/operators";

export interface ContactPaginationResult {
  page: number;
  totalPages: number;
  results: [];
}


export interface ContactPaginationQuery {
  page: number;
  pageSize?: number;
}

export interface JobRequest {

  id: number;
  jobProvided: string;
  jobId: number;
  message: string;
  status: string;
  provider: User;
  user: User;
  date: Date;
  contactInfo: ContactInfo;

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
        this.results.next(res.body);
      }
    });
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



  changeContactStatus(contactId:number, status:string){
    return this.http.put(
      environment.apiBaseUrl + '/user/requests/'+contactId,
      {
        status
      }
      );
  }

}
