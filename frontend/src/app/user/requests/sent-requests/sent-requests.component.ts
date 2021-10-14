import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RequestPaginationQuery, RequestPaginationResult, RequestsService} from "../requests.service";
import {Subscription} from "rxjs";
import {ContactOrder} from "../../../models/contact-order-enum.model";

@Component({
  selector: 'app-sent-requests',
  templateUrl: './sent-requests-card.component.html',
  styleUrls: ['./sent-requests-card.component.scss']
})
export class SentRequestsComponent implements OnInit, OnDestroy {

  rpq: RequestPaginationQuery = {
    page: 0,
    pageSize: 4,
    order: ContactOrder.NEWEST
  };

  rpr: RequestPaginationResult = {
    results: [],
    totalPages: 0,
  }

  isFetching = true;

  @Input("status") filterOptions: string[] = [];

  @Input("orderOptions") orderOptions: string[] = [];

  private contactSub: Subscription;

  constructor(
    private contactService: RequestsService,
  ) {
  }

  ngOnInit(): void {
    this.contactService.getUserSentRequests(this.rpq);
    this.contactSub = this.contactService.sentRequests.subscribe((results) => {
      this.rpr = {
        ...this.rpr,
        ...results
      };
      this.isFetching = false;
    });

  }
  onChangePage(page: number) {
    this.rpq.page = page;
    this.contactService.getUserSentRequests(this.rpq);
  }

  onChangeStatus(status: string) {
    this.rpq.status = status;
    this.rpq.page = 0;
    if (!status) {
      delete this.rpq.status;
    }
    this.contactService.getUserSentRequests(this.rpq);
  }

  onChangeOrder(order: string) {
    this.rpq.order = order;
    this.rpq.page = 0;
    this.contactService.getUserSentRequests(this.rpq);
  }

  ngOnDestroy(): void {
    this.contactSub.unsubscribe();
  }

}
