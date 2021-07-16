import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";

@Component({
  selector: 'app-new-follower-notification-card',
  templateUrl: './new-follower-notification-card.component.html',
  styleUrls: ['./new-follower-notification-card.component.scss']
})
export class NewFollowerNotificationCard implements OnInit {

  @Input("notification") notification: Notification;

  constructor() {
  }

  ngOnInit(): void {
  }

}
