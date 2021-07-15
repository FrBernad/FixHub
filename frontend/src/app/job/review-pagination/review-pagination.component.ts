import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Job} from "../../models/job.model";
import {JobService, ReviewsPaginationQuery, ReviewsPaginationResult} from "../job.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-review-pagination',
  templateUrl: './review-pagination.component.html',
  styleUrls: ['./review-pagination.component.scss']
})
export class ReviewPaginationComponent implements OnInit, OnDestroy {

  @Input("job") job: Job;

  reviewsSub: Subscription;

  rpr: ReviewsPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  rpq: ReviewsPaginationQuery = {
    page: 0,
    pageSize: 4,
  }

  constructor(
    private jobService: JobService
  ) {
  }

  ngOnInit(): void {
    this.jobService.getReviews(this.rpq, this.job.id);
    this.reviewsSub = this.jobService.reviews.subscribe((results) => {
      this.rpr = {
        ...this.rpr,
        ...results
      };
    });
  }

  onChangePage(page: number) {
    this.rpq.page = page;
    this.jobService.getReviews(this.rpq, this.job.id);
  }

  resetReviews() {
    this.rpq.page = 0;
    this.jobService.getReviews(this.rpq, this.job.id);
  }

  ngOnDestroy(): void {
    this.reviewsSub.unsubscribe();
  }

}
