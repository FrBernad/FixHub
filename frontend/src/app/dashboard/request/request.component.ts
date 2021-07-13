import { Component, OnInit } from '@angular/core';
import {ContactService, RequestPaginationQuery, RequestPaginationResult} from "../../job/contact/contact.service";
import {FilterStatusRequestModel} from "../../models/filterStatusRequest.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.scss']
})
export class RequestComponent implements OnInit {

  rpq: RequestPaginationQuery = {
    page:0,
    pageSize:4,
    filter:FilterStatusRequestModel.PENDING,
  };

  rpr: RequestPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  private contactSub:Subscription;


  constructor( private contactService: ContactService) { }

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


  ngOnDestroy(): void {
    this.contactSub.unsubscribe();
  }

}
