import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ContactPaginationQuery, ContactPaginationResult, ContactService} from "../../../job/contact/contact.service";


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

  constructor(private contactService: ContactService) { }

  ngOnInit(): void {
    this.contactService.getUserSentRequests(this.cpq);
    this.contactSub = this.contactService.results.subscribe((results) => {
      console.log(results);
      this.cpr = {
        ...this.cpr,
        ...results
      };
    });
  }

  onChangePage(page: number) {
    this.cpq.page = page;
    this.contactService.getUserSentRequests(this.cpq);
  }

  ngOnDestroy(): void {
    this.contactSub.unsubscribe();
  }

}
