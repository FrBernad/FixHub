import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";

@Component({
  selector: 'app-new-job-request-notification-card',
  templateUrl: './new-job-request-notification-card.component.html',
  styleUrls: ['./new-job-request-notification-card.component.scss']
})
export class NewJobRequestNotificationCardComponent implements OnInit {

  @Input("notification") notification: Notification;

  constructor() { }

  ngOnInit(): void {
  }

}
