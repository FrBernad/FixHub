import {Component, OnInit} from '@angular/core';
import {User} from "../models/user.model";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {JobService, ReviewsPaginationResult} from "./job.service";
import {Subscription} from "rxjs";
import {UserService} from "../auth/services/user.service";
import {SingleJob} from "../models/single-job.model";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit {

  job: SingleJob;

  selectedIndex = 0;
  isFetching = true;

  private reviewsSub: Subscription;

  rpr: ReviewsPaginationResult = {
    results: [],
    totalPages: 0,
  }

  userSub: Subscription;
  loggedUser: User;
  user: User;

  constructor(
    private jobService: JobService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private titleService: Title,
  ) {
  }

  ngOnInit(): void {

    this.userSub = this.userService.user.subscribe((user) => {
      this.loggedUser = user;
    });

    this.jobService.getJob(this.route.snapshot.params['id']).subscribe(
      job => {
        this.job = job;
        this.isFetching = false;
        this.titleService.setTitle('Fixhub | ' + job.jobProvided)
        this.jobService.getFirstReviews(+this.job.id);
      },
      () => {
        this.router.navigate(['/404']);
      }
    );

    this.reviewsSub = this.jobService.firstReviews.subscribe((results) => {
      this.rpr = results;
    });

  }

  selectPrevious() {
    if (this.selectedIndex == 0) {
      this.selectedIndex = this.job.imagesUrls.length - 1;
    } else {
      this.selectedIndex--;
    }
  }

  selectNext() {
    if (this.selectedIndex == this.job.imagesUrls.length - 1) {
      this.selectedIndex = 0;
    } else {
      this.selectedIndex++;
    }
  }

  ngOnDestroy(): void {
    this.reviewsSub.unsubscribe();
    this.userSub.unsubscribe();
  }

}
