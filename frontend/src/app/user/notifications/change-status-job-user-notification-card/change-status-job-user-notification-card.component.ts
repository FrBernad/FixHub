import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";
import {RequestsService} from "../../requests/requests.service";
import {JobRequest} from "../../../models/job-request.model";
import {NotificationsService} from "../notifications.service";
import {Router} from "@angular/router";
import {NotificationType} from "../../../models/notification-type-enum.model";

@Component({
  selector: 'app-change-status-job-user-notification-card',
  templateUrl: './change-status-job-user-notification-card.component.html',
  styleUrls: ['./change-status-job-user-notification-card.component.scss']
})
export class ChangeStatusJobUserNotificationCardComponent implements OnInit {

  @Input("notification") notification: Notification;

  jobRequest: JobRequest;
  requestRejected = NotificationType.REQUEST_STATUS_CHANGE_USER_REJECTED;
  requestAccepted = NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED;
  requestFinished = NotificationType.REQUEST_STATUS_CHANGE_USER_FINISHED;

  isLoading = true;

  constructor(
    private notificationService: NotificationsService,
    private contactService: RequestsService,
    private router: Router
  ){
  }

  ngOnInit(): void {
    this.contactService.getSentJobRequest(this.notification.resource).subscribe((jobRequest) => {
      this.jobRequest = jobRequest;
      this.isLoading = false;
    });
  }

  notificationClick() {
    this.notificationService.markAsReadNotification(this.notification.id).subscribe(
      () => {
        this.notification.seen = true;
      }
    );
    this.router.navigate(['/user/requests/sent', this.jobRequest.id]);
  }

}
