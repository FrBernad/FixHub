import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";
import {ContactService} from "../../../job/contact/contact.service";
import {JobRequest} from "../../../models/job-request.model";
import {NotificationsService} from "../notifications.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-job-request-notification-card',
  templateUrl: './new-job-request-notification-card.component.html',
  styleUrls: ['./new-job-request-notification-card.component.scss']
})
export class NewJobRequestNotificationCardComponent implements OnInit {

  @Input("notification") notification: Notification;

  jobRequest: JobRequest;

  constructor(private contactService: ContactService,
              private notificationService: NotificationsService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.contactService.getProviderJobRequest(this.notification.resource).subscribe((jobRequest) => {
      this.jobRequest = jobRequest;
    });
  }

  notificationClick() {
    this.notificationService.markAsReadNotification(this.notification.id).subscribe(
      () => {
        this.notification.seen = true;
      }
    );
    this.router.navigate(['/user', 'requests', this.jobRequest.id]);
  }

}
