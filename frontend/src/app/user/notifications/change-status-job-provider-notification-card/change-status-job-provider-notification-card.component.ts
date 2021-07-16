import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";
import {JobRequest} from "../../../models/job-request.model";
import {NotificationsService} from "../notifications.service";
import {ContactService} from "../../../job/contact/contact.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-change-staus-job-provider-notification-card',
  templateUrl: './change-status-job-provider-notification-card.component.html',
  styleUrls: ['./change-status-job-provider-notification-card.component.scss']
})
export class ChangeStatusJobProviderNotificationCard implements OnInit {

  @Input("notification") notification: Notification;

  jobRequest: JobRequest;

  isLoading = true;

  constructor(
    private notificationService: NotificationsService,
    private contactService: ContactService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.contactService.getJobRequest(this.notification.resource).subscribe((jobRequest) => {
      this.jobRequest = jobRequest;
    });
  }

  onClick() {
    this.notificationService.markAsReadNotification(this.notification.id).subscribe(
      () => {
        this.notification.seen = true;
      }
    );
    this.router.navigate(['/user', 'requests', this.jobRequest.id]);
  }

}
