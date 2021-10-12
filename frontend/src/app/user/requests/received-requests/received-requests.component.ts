import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RequestPaginationQuery, RequestPaginationResult, RequestsService} from "../requests.service";
import {Subscription} from "rxjs";
import {ContactOrder} from "../../../models/contact-order-enum.model";

@Component({
  selector: 'app-received-requests',
  templateUrl: './received-requests.component.html',
  styleUrls: ['./received-requests.component.scss']
})
export class ReceivedRequestsComponent implements OnInit, OnDestroy {

  isFetching = false;

  rpq: RequestPaginationQuery = {
    page: 0,
    pageSize: 4,
    order: ContactOrder.NEWEST
  };

  rpr: RequestPaginationResult = {
    results: [],
    totalPages: 0,
  }

  @Input("status") filterOptions: string[] = [];

  @Input("orderOptions") orderOptions: string[] = [];

  private contactSub: Subscription;

  constructor(
    private contactService: RequestsService
  ) {
  }

  ngOnInit(): void {
    this.contactService.getProviderRequests(this.rpq);
    this.contactSub = this.contactService.receivedRequests.subscribe((results) => {
      this.isFetching = false;
      this.rpr = {
        ...this.rpr,
        ...results
      };
    });
  }

  onChangePage(page: number) {
    this.rpq.page = page;
    this.contactService.getProviderRequests(this.rpq);
  }

  onChangeStatus(status: string) {
    this.rpq.status = status;
    this.rpq.page = 0;
    if (!status) {
      delete this.rpq.status;
    }
    this.contactService.getProviderRequests(this.rpq);
  }

  onChangeOrder(order: string) {
    this.rpq.order = order;
    this.rpq.page = 0;
    this.contactService.getProviderRequests(this.rpq);
  }

  ngOnDestroy(): void {
    this.contactSub.unsubscribe();
  }

}
