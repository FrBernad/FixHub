import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ContactPaginationQuery, ContactPaginationResult, ContactService} from "../../job/contact/contact.service";
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {

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
    private titleService: Title,
    private translateService: TranslateService,
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

    this.translateService.get("requests.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )

  }

  onChangePage(page: number) {
    this.cpq.page = page;
    this.contactService.getUserSentRequests(this.cpq);
  }

  ngOnDestroy(): void {
    this.contactSub.unsubscribe();
  }

}
