import {Component, OnDestroy, OnInit} from '@angular/core';
import {DiscoverService, JobPaginationQuery, JobPaginationResult} from "../../../../discover/discover.service";
import {OrderOption} from "../../../../models/order-option-enum.model";
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
    totalPages: 0,
  }

  jpq: JobPaginationQuery = {
    page: 0,
    pageSize: 4,
    order: OrderOption.MOST_POPULAR
  }

  searchError = false;

  private jobsSub: Subscription;
  private searchOptionsSub: Subscription;

  maxSearchInputLength: number = 50;

  orderOptions: string[] = [];

  constructor(
    private worksService: WorksService,
    private jobsService: DiscoverService,
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

    this.jobsService.getSearchOptions();

    this.searchOptionsSub = this.jobsService.searchOptions.subscribe((searchOptions) => {
      if (!!searchOptions) {
        this.orderOptions = searchOptions.find((option) => {
          return option.key === "order"
        }).values
      }
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
    this.searchOptionsSub.unsubscribe();
  }

}
