import { Component, OnInit } from '@angular/core';
import {ContactPaginationQuery, ContactPaginationResult, ContactService} from "../../../job/contact/contact.service";
import {Subscription} from "rxjs";
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-sent-requests',
  templateUrl: './sent-requests-card.component.html',
  styleUrls: ['./sent-requests-card.component.scss']
})
export class SentRequestsComponent implements OnInit {

  cpr: ContactPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  cpq: ContactPaginationQuery = {
    page: 0,
    pageSize: 4,
  }

  isFetching = true;

  private contactSub: Subscription;

  constructor(
    private contactService: ContactService,
  ) {
  }

  ngOnInit(): void {
    this.contactService.getUserSentRequests(this.cpq);
    this.contactSub = this.contactService.results.subscribe((results) => {
      this.cpr = {
        ...this.cpr,
        ...results
      };
      this.isFetching = false;
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
