import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";
import {ContactService} from "../../../job/contact/contact.service";
import {JobRequest} from "../../../models/job-request.model";
import {NotificationsService} from "../notifications.service";
import {Router} from "@angular/router";
import {JobStatus} from "../../../models/job-status-enum.model";

@Component({
  selector: 'app-change-status-job-user-notification-card',
  templateUrl: './change-status-job-user-notification-card.component.html',
  styleUrls: ['./change-status-job-user-notification-card.component.scss']
})
export class ChangeStatusJobUserNotificationCardComponent implements OnInit {

  @Input("notification") notification: Notification;

  jobRequest: JobRequest;
  requestRejected = JobStatus.REJECTED;
  requestAccepted = JobStatus.IN_PROGRESS;
  requestFinished = JobStatus.FINISHED;

  isLoading = true;

  constructor(
    private notificationService: NotificationsService,
    private contactService: ContactService,
    private router: Router
  ){
  }

  ngOnInit(): void {
    this.contactService.getJobRequest(this.notification.resource).subscribe((jobRequest) => {
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
    this.router.navigate(['/user','requests', this.jobRequest.id]);
  }

}
