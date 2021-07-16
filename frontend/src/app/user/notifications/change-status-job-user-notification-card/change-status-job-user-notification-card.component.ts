import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";

@Component({
  selector: 'app-change-status-job-user-notification-card',
  templateUrl: './change-status-job-user-notification-card.component.html',
  styleUrls: ['./change-status-job-user-notification-card.component.scss']
})
export class ChangeStatusJobUserNotificationCardComponent implements OnInit {

  @Input("notification") notification: Notification;


  constructor() { }

  ngOnInit(): void {
  }

}
