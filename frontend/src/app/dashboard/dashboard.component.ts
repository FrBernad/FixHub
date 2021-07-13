import {Component, OnDestroy, OnInit} from '@angular/core';
import {OrderOptionModel} from '../models/orderOption.model';
import {JobPaginationQuery, JobPaginationResult, JobsService} from "../discover/jobs.service";
import {Job} from "../models/job.model";
import {ProviderDetails, User} from "../models/user.model";
import {JobCategoryModel} from "../models/jobCategory.model";
import {UserService} from "../auth/user.service";
import {Contact} from "../job/contact/contact.model";
import {Subscription} from "rxjs";
import {JobStatusModel} from "../models/job-status.model";
import {ContactInfo} from "../models/contactInfo.model";
import {ContactService} from "../job/contact/contact.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  activeTab: string = 'dashboard';

  orderOptions = Object.keys(OrderOptionModel).filter((item) => {
    return isNaN(Number(item));
  });

  private userSub: Subscription;
  user: User;

  constructor(
    private userService: UserService,
  ) {
  }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user =>{
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
