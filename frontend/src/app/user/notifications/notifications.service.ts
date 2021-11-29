import {HttpClient, HttpParams, HttpResponse, HttpStatusCode} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Notification} from "../../models/notification.model";
import {Subject} from "rxjs";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {UserService} from "../../auth/services/user.service";

export interface NotificationPaginationResult {
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
    totalPages: 0,
  }

  notifications = new Subject<NotificationPaginationResult>();
  newNotifications = new Subject<boolean>();

  private notificationsInterval: any;

  constructor(
    private http: HttpClient,
    private router: Router,
    private userService: UserService,
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
        environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/notifications/unseen'
      ).subscribe((res) => {
        if (res.count > 0) {
          this.newNotifications.next(true);
        } else {
          this.newNotifications.next(false);
        }
      }
      , (_) => {
      });
  }

  getNotifications(npq: NotificationPaginationQuery) {
    this.http
      .get<Notification[]>(
        environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/notifications',
        {
          observe: "response",
          params: new HttpParams({fromObject: {...npq}})
        },
      ).subscribe((res) => {
        if (res.status === HttpStatusCode.NoContent) {
          this.notifications.next({
            totalPages: 0,
            results: []
          });
        } else {
          const nr: NotificationPaginationResult = this.parsePaginationResult(res);
          this.notifications.next(nr);
        }
      },
      () => {
        this.router.navigate(['500'])
      }
    );
  }


  markAsReadNotification(id: number) {
    return this.http.put(
      environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/notifications/' + id, {})
      .pipe(
        tap(() => {
            this.refreshNotifications();
          }
        )
      );
  }

  markAsReadAllNotifications() {
    return this.http.put(
      environment.apiBaseUrl + '/users/' + this.userService.user.getValue().id + '/notifications', {})
      .pipe(
        tap(() => {
            this.refreshNotifications();
          }
        )
      );
  }


  private parsePaginationResult(res: HttpResponse<Notification[]>): NotificationPaginationResult {

    const lastLink: string = res.headers
      .getAll('Link')
      .pop()
      .split(',')
      .filter((link) => (link.includes("last")))
      .pop()
      .match(/<(.*)>/)[1];

    const totalPages: number = Number(new URL(lastLink).searchParams.get("page")) + 1;

    return {
      totalPages,
      results: res.body
    }
  }

}
