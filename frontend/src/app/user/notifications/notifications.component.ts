import {Component, OnDestroy, OnInit} from '@angular/core';
import {NotificationPaginationQuery, NotificationPaginationResult, NotificationsService} from "./notifications.service";
import {Subscription} from "rxjs";
import {NotificationFilter} from "../../models/notification-filter-enum";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit, OnDestroy {

  loading = true;

  private notificationSub: Subscription;

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
    if (!filter) {
      delete this.npq.onlyNew;
    }
    this.notificationsService.getNotifications(this.npq);
  }

  loadMore() {
    if (this.canLoadMore()) {
      this.npq.page = this.npq.page + 1;
      this.notificationsService.getNotifications(this.npq);
    }
  }


}
