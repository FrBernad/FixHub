import {Component, OnDestroy, OnInit} from '@angular/core';
import {NotificationPaginationQuery, NotificationPaginationResult, NotificationsService} from "./notifications.service";
import {Subscription} from "rxjs";
import {NotificationFilter} from "../../models/notification-filter-enum";
import {NotificationType} from "../../models/notification-type-enum.model";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit, OnDestroy {

  loading = true;
  loadingMore = false;
  asSeenLoading = false;
  private notificationSub: Subscription;
  private transSub: Subscription;

  jobRequest = NotificationType.JOB_REQUEST;
  newfollower = NotificationType.NEW_FOLLOWER;
  requestStatusChangeProvider = NotificationType.REQUEST_STATUS_CHANGE_PROVIDER;

  npq: NotificationPaginationQuery = {
    page: 0,
    pageSize: 6,
    onlyNew: true
  }

  npr: NotificationPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  filterOptions = Object.keys(NotificationFilter).filter((item) => {
    return isNaN(Number(item));
  });


  constructor(
    private notificationsService: NotificationsService,
    private titleService: Title,
    private translateService: TranslateService
  ) {
  }

  ngOnInit(): void {

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });

    this.notificationsService.getNotifications(this.npq);
    this.notificationSub = this.notificationsService.notifications.subscribe((results) => {
        if (this.npq.page == 0) {
          this.npr = results;
        } else {
          this.npr.totalPages = results.totalPages;
          this.npr.page = results.page;
          this.npr.results.push(...results.results);
        }
        this.loading = false;
        this.loadingMore = false;
      }, () => {
        this.loading = false;
        this.loadingMore = false;
      }
    )
  }

  changeTitle() {
    this.translateService.get("account.notifications.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  canLoadMore(): boolean {
    return this.npq.page < this.npr.totalPages - 1;
  }

  onChangeFilter(filter: string) {
    this.npq.onlyNew = !!filter;
    this.npq.page = 0;
    this.notificationsService.getNotifications(this.npq);
  }

  loadMore() {
    this.loadingMore = true;
    if (this.canLoadMore()) {
      this.npq.page = this.npq.page + 1;
      this.notificationsService.getNotifications(this.npq);
    }
  }

  markAllAsSeen() {
    this.asSeenLoading = true;
    this.npq.page = 0;
    this.notificationsService.markAsReadAllNotifications()
      .subscribe(
        () => {
          this.asSeenLoading = false;
          this.notificationsService.getNotifications(this.npq);
        },
        () => {
          this.asSeenLoading = false;
        }
      );
  }

  ngOnDestroy(): void {
    this.notificationSub.unsubscribe();
    this.transSub.unsubscribe();
  }

}
