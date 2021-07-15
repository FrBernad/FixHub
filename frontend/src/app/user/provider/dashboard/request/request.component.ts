import {Component, OnInit} from '@angular/core';
import {ContactService, RequestPaginationQuery, RequestPaginationResult} from "../../../../job/contact/contact.service";
import {FilterStatusRequest} from "../../../../models/filter-status-request-enum.model";
import {Subscription} from "rxjs";
import {ContactOrder} from "../../../../models/contact-order-enum.model";

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.scss']
})
export class RequestComponent implements OnInit {

  rpq: RequestPaginationQuery = {
    page: 0,
    pageSize: 4,
    order: ContactOrder.NEWEST
  };

  rpr: RequestPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  filterOptions = Object.keys(FilterStatusRequest).filter((item) => {
    return isNaN(Number(item));
  });

  orderOptions = Object.keys(ContactOrder).filter((item) => {
    return isNaN(Number(item));
  });

  private contactSub: Subscription;

  constructor(private contactService: ContactService) {
  }

  ngOnInit(): void {
    this.contactService.getProviderRequests(this.rpq);
    this.contactSub = this.contactService.results.subscribe((results) => {
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
