import {Component, OnDestroy, OnInit} from '@angular/core';
import {JobsService} from "../../../discover/jobs.service";
import {UserService} from "../../../auth/services/user.service";
import {Subscription} from "rxjs";
import {User} from "../../../models/user.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  activeTab: string = 'dashboard';

  private userSub: Subscription;
  user: User;

  constructor(
    private jobsService: JobsService,
    private userService: UserService,
  ) {
  }

  ngOnInit(): void {
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
