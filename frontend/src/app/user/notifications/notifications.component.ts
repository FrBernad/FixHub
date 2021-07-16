import {Component, OnDestroy, OnInit} from '@angular/core';
import {NotificationPaginationQuery, NotificationPaginationResult, NotificationsService} from "./notifications.service";
import {Subscription} from "rxjs";
import {NotificationFilter} from "../../models/notification-filter-enum";
import {NotificationType} from "../../models/notification-type-enum.model";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit, OnDestroy {

  loading = true;

  private notificationSub: Subscription;

  jobRequest = NotificationType.JOB_REQUEST;
  newfollower = NotificationType.NEW_FOLLOWER;
  requestStatusChangeProvider = NotificationType.REQUEST_STATUS_CHANGE_PROVIDER;
  requestStatusChangeUser= NotificationType.REQUEST_STATUS_CHANGE_USER;

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
  ) {
  }

  ngOnInit(): void {
    this.loading = false;
    this.notificationsService.getNotifications(this.npq);
    this.notificationSub = this.notificationsService.notifications.subscribe((results) => {
      if (this.npq.page == 0) {
        this.npr = results;
      } else {
        this.npr.totalPages = results.totalPages;
        this.npr.page = results.page;
        this.npr.results.push(...results.results);
      }
    })
  }

  ngOnDestroy(): void {
    this.notificationSub.unsubscribe();
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
    if (this.canLoadMore()) {
      this.npq.page = this.npq.page + 1;
      this.notificationsService.getNotifications(this.npq);
    }
  }




}
