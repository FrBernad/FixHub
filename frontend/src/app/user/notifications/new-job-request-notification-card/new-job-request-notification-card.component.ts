import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";
import {RequestsService} from "../../requests/requests.service";
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
  isLoading=true;

  constructor(private contactService: RequestsService,
              private notificationService: NotificationsService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.contactService.getJobRequest(this.notification.resource).subscribe((jobRequest) => {
      this.jobRequest = jobRequest;
      this.isLoading=false;
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
