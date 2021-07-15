import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../auth/services/user.service";
import {User} from "../../../models/user.model";
import {FollowPaginationQuery, FollowPaginationResult, FollowService} from "../follow.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-following',
  templateUrl: '../follows.component.html',
  styleUrls: ['../follows.component.scss']
})
export class FollowingComponent implements OnInit, OnDestroy {

  user: User;
  loading = true;

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
    private userService: UserService,
    private followService: FollowService
  ) {
  }

  ngOnInit(): void {
    const userId = this.route.snapshot.params['id'];
    this.userService.getUser(userId).subscribe(
      (user) => {
        this.user = user;
        this.loading = false;
        this.followService.getFollowing(this.fpq, this.user.id);
        this.followSub = this.followService.follows.subscribe((results) => {
          this.fpr = {
            ...this.fpr,
            ...results
          };
        });
      }
    );
  }

  onChangePage(page: number) {
    this.fpq.page = page;
    this.followService.getFollowing(this.fpq, this.user.id);
  }

  ngOnDestroy(): void {
    if (this.followSub) {
      this.followSub.unsubscribe();
    }
  }

}
