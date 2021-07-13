import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams, HttpStatusCode} from '@angular/common/http';
import {ContactInfo} from 'src/app/models/contactInfo.model';
import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {User} from "../../models/user.model";

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

  id:number;
  jobProvided:string;
  message:string;
  status:string;
  provider:User;
  user:User;
  date:Date;
  contactInfo: ContactInfo;

}

export interface RequestPaginationResult {
  page: number;
  totalPages: number;
  results: [];
}

export interface RequestPaginationQuery {
  page: number;
  pageSize?: number;
  filter?:string;
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

@Injectable({ providedIn: 'root' })
export class ContactService {

  results = new Subject<ContactPaginationResult>();


  constructor(private http: HttpClient) {}

  getContactInfo(id: number) {
    return this.http.get<ContactInfo[]>(
      environment.apiBaseUrl + '/users/' + id + '/contactInfo'
    );
  }

  newContact(jobId: number, contactData: ContactData) {
    return this.http.post<ContactData>(environment.apiBaseUrl + '/jobs/' + jobId + '/contact', contactData);
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

}
