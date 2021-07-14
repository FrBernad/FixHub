import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {JobPaginationQuery, JobPaginationResult} from "../../discover/jobs.service";
import {OrderOption} from "../../models/order-option-enum.model";
import {Subscription} from "rxjs";
import {WorksService} from "./works.service";

@Component({
  selector: 'app-works',
  templateUrl: './works.component.html',
  styleUrls: ['./works.component.scss']
})
export class WorksComponent implements OnInit, OnDestroy {

  jpr: JobPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  jpq: JobPaginationQuery = {
    page: 0,
    pageSize: 4,
    order: OrderOption.MOST_POPULAR
  }

  searchError = false;

  private jobsSub: Subscription;

  orderOptions = Object.keys(OrderOption).filter((item) => {
    return isNaN(Number(item));
  });

  constructor(
    private worksService: WorksService,
  ) {
  }

  ngOnInit(): void {
    this.worksService.getUserJobs(this.jpq);
    this.jobsSub = this.worksService.results.subscribe((results) => {
      this.jpr = {
        ...this.jpr,
        ...results
      };
    });
  }

  onChangeOrder(order: string) {
    this.jpq.order = order;
    this.jpq.page = 0;
    this.worksService.getUserJobs(this.jpq);
  }

  onChangePage(page: number) {
    this.jpq.page = page;
    this.worksService.getUserJobs(this.jpq)
  }

  onSearchEnter(e: KeyboardEvent, query: string) {
    if (e.key === "Enter") {
      this.onSearch(query);
    }
  }

  onSearch(query: string) {
    if (!this.searchError) {
      this.jpq.page = 0;
      this.jpq.query = query;
      this.worksService.getUserJobs(this.jpq);
    }
  }

  checkLength(query: string) {
    this.searchError = query.length > 50;
  }

  ngOnDestroy(): void {
    this.jobsSub.unsubscribe();
  }

}
