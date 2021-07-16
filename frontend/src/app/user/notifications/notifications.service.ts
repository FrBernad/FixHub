import {HttpClient, HttpParams, HttpStatusCode} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Notification} from "../../models/notification.model";
import {Subject} from "rxjs";
import {environment} from "../../../environments/environment";

export interface NotificationPaginationResult {
  page: number;
  totalPages: number;
  results: Notification[];
}

export interface NotificationPaginationQuery {
  page: number;
  pageSize?: number;
  onlyNew?: boolean;
}

@Injectable({providedIn: 'root'})
export class NotificationsService {

  rpr: NotificationPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  notifications = new Subject<NotificationPaginationResult>();
  newNotifications = new Subject<boolean>();

  private notificationsInterval: any;

  constructor(
    private http: HttpClient
  ) {
  }

  initNotificationsInterval() {
    this.clearNotificationsInterval();
    this.refreshNotifications();
    this.notificationsInterval = setInterval(this.refreshNotifications.bind(this), 30000);
  }

  clearNotificationsInterval() {
    if (this.notificationsInterval) {
      clearTimeout(this.notificationsInterval);
    }
    this.notificationsInterval = null;
  }

  refreshNotifications() {
    this.http
      .get<{ count: number }>(
        environment.apiBaseUrl + '/user/unseenNotifications'
      ).subscribe((res) => {
        if (res.count > 0) {
          this.newNotifications.next(true);
        } else {
          this.newNotifications.next(false);
        }
      }
    );
  }

  getNotifications(npq: NotificationPaginationQuery) {
    this.http
      .get<NotificationPaginationResult>(
        environment.apiBaseUrl + '/user/notifications',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...npq}})
        },
      ).subscribe((res) => {
      if (res.status === HttpStatusCode.NoContent) {
        this.notifications.next({
          page: 0,
          totalPages: 0,
          results: []
        });
      } else {
        this.notifications.next(res.body);
      }
    });
  }


}
