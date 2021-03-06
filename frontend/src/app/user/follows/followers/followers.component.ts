import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../auth/services/user.service";
import {User} from "../../../models/user.model";
import {FollowPaginationQuery, FollowPaginationResult, FollowService} from "../follow.service";
import {Subscription} from "rxjs";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-followers',
  templateUrl: '../follows.component.html',
  styleUrls: ['../follows.component.scss']
})
export class FollowersComponent implements OnInit, OnDestroy {

  private transSub: Subscription;

  user: User;
  loading = true;
  loadingFollow = true;
  noData = "profilePage.noFollowers"

  fpr: FollowPaginationResult = {
    results: [],
    totalPages: 0,
  }

  fpq: FollowPaginationQuery = {
    page: 0,
    pageSize: 4,
  }

  followersSub: Subscription;

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

        this.route.queryParams.subscribe(() => {
          this.parseQueryParams();
          this.followService.getFollowers(this.fpq, this.user.id);
        })

        this.followersSub = this.followService.followers.subscribe((results) => {
          this.fpr = {
            ...this.fpr,
            ...results
          };
          this.loadingFollow = false;
        });
      },
      () => {
        this.router.navigate(['/404'])
      }
    );

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });
  }

  changeTitle() {
    this.translateService.get("profilePage.followers")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  onChangePage(page: number) {
    this.fpq.page = page;
    this.updateRoute(false);
  }

  private parseQueryParams() {
    const params = this.route.snapshot.queryParams;

    this.fpq = {
      ...this.fpq,
      ...params
    }

    if (params["page"]) {
      this.fpq.page = Number.parseInt(params["page"])
      this.fpq.page = isNaN(this.fpq.page) ? 0 : this.fpq.page;
    } else {
      this.fpq.page = 0;
    }

    if (params["pageSize"]) {
      this.fpq.pageSize = Number.parseInt(params["pageSize"])
      this.fpq.pageSize = isNaN(this.fpq.pageSize) ? 4 : this.fpq.pageSize;
    } else {
      this.fpq.pageSize = 4
    }


  }

  private updateRoute(replace: boolean) {
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: {...this.fpq},
        replaceUrl: replace
      });
  }

  ngOnDestroy(): void {
    if (this.followersSub) {
      this.followersSub.unsubscribe();
    }

    if (this.transSub) {
      this.transSub.unsubscribe();
    }
  }

}
