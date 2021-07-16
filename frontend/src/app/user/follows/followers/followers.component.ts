import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../auth/services/user.service";
import {User} from "../../../models/user.model";
import {FollowPaginationQuery, FollowPaginationResult, FollowService} from "../follow.service";
import {Subscription} from "rxjs";
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-followers',
  templateUrl: '../follows.component.html',
  styleUrls: ['../follows.component.scss']
})
export class FollowersComponent implements OnInit, OnDestroy {

  user: User;
  loading = true;
  noData = "profilePage.noFollowers"

  fpr: FollowPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  fpq: FollowPaginationQuery = {
    page: 0,
    pageSize: 4,
  }

  followSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private followService: FollowService,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {
    const userId = this.route.snapshot.params['id'];
    this.userService.getUser(userId).subscribe(
      (user) => {
        this.user = user;
        this.loading = false;
        this.followService.getFollowers(this.fpq, this.user.id);
        this.followSub = this.followService.follows.subscribe((results) => {
          this.fpr = {
            ...this.fpr,
            ...results
          };
        });
      },
      () => {
        this.router.navigate(['/404'])
      }
    );
    this.translateService.get("profilePage.following")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  onChangePage(page: number) {
    this.fpq.page = page;
    this.followService.getFollowers(this.fpq, this.user.id);
  }

  ngOnDestroy(): void {
    if (this.followSub) {
      this.followSub.unsubscribe();
    }
  }

}
