import {Component, OnDestroy, OnInit} from '@angular/core';
import {OrderOptionModel} from '../models/orderOption.model';
import {JobPaginationQuery, JobPaginationResult, JobsService} from "../discover/jobs.service";
import {User} from "../models/user.model";
import {UserService} from "../auth/user.service";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  jpr: JobPaginationResult = {
    results: [],
    page: 0,
    totalPages: 4,
  };

  jpq: JobPaginationQuery = {
    page: 0,
    pageSize: 4,
    order: OrderOptionModel.MOST_POPULAR
  };

  orderOptions = Object.keys(OrderOptionModel).filter((item) => {
    return isNaN(Number(item));
  });

  private userSub: Subscription;
  user: User;

  activeTab: string = 'dashboard';

  constructor(
    private jobsService: JobsService,
    private userService: UserService,
  ) {
  }

  ngOnInit(): void {
    this.jobsService.getJobs(this.jpq);
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    })

  }

  changeTab(tab: string) {
    this.activeTab = tab;
  }



  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }

}
