import {Component, OnInit} from '@angular/core';
import {Contact} from "../../../job/contact/contact.model";
import {Subscription} from "rxjs";
import {UserSentRequestService} from "./user-sent-request.service";
import {User} from "../../../models/user.model";

export interface ContactPaginationResult {
  page: number;
  totalPages: number;
  results: Contact[];
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

}

@Component({
  selector: 'app-user-sent-request',
  templateUrl: './user-sent-request.component.html',
  styleUrls: ['./user-sent-request.component.scss','../session-profile.component.scss']
})
export class UserSentRequestComponent implements OnInit {

  cpr: ContactPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  cpq: ContactPaginationQuery = {
    page: 0,
    pageSize: 4,
  }


  private contactSub:Subscription;


  constructor(private userContactService: UserSentRequestService) { }

  ngOnInit(): void {
    this.userContactService.getUserSentRequests(this.cpq);
    this.contactSub = this.userContactService.results.subscribe((results) => {
      this.cpr = {
        ...this.cpr,
        ...results
      };
    });
  }

  onChangePage(page: number) {
    this.cpq.page = page;
    this.userContactService.getUserSentRequests(this.cpq);
  }

  ngOnDestroy(): void {
    this.contactSub.unsubscribe();
  }

}
