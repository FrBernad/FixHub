import {Component, OnInit} from '@angular/core';
import {Job} from "../models/job.model";
import {User} from "../models/user.model";
import {ActivatedRoute, Params} from "@angular/router";
import {JobService, ReviewsPaginationResult} from "./job.service";
import {Subscription} from "rxjs";
import {Review} from "./review.model";
import {JobPaginationResult} from "../discover/jobs.service";
import {UserService} from "../auth/user.service";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit {

  job: Job = new Job();

  selectedIndex = 0;
  isFetching = true;

  private reviewsSub: Subscription;

  rpr: ReviewsPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  userSub: Subscription;
  loggedUser: User;
  user: User;

  constructor(
    private route: ActivatedRoute,
    private jobService: JobService,
    private userService:UserService
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.job.id = params['id'];
      }
    );

    this.userSub = this.userService.user.subscribe((user) => {
      this.loggedUser = user;
    });

    this.jobService.getJob(+this.job.id).subscribe(
      job => {
        this.job = job;
        this.isFetching = false;
      }
    );

    this.reviewsSub = this.jobService.firstReviews.subscribe((results) => {
      this.rpr = results;
    });

    this.jobService.getFirstReviews(+this.job.id);

  }

  selectPrevious() {
    if (this.selectedIndex == 0) {
      this.selectedIndex = this.job.images.length - 1;
    } else {
      this.selectedIndex--;
    }
  }

  selectNext() {
    if (this.selectedIndex == this.job.images.length - 1) {
      this.selectedIndex = 0;
    } else {
      this.selectedIndex++;
    }
  }

  ngOnDestroy(): void {
    this.reviewsSub.unsubscribe();
  }

}
