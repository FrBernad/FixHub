import {Component, OnDestroy, OnInit} from '@angular/core';
import {DiscoverService} from "../../../discover/discover.service";
import {UserService} from "../../../auth/services/user.service";
import {Subscription} from "rxjs";
import {User} from "../../../models/user.model";
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";

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
    private jobsService: DiscoverService,
    private userService: UserService,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    })

    this.translateService.get("navBar.dashboard")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )

  }

  changeTab(tab: string) {
    this.activeTab = tab;
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }

}
