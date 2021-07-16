import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";

@Component({
  selector: 'app-change-staus-job-provider-notification-card',
  templateUrl: './change-staus-job-provider-notification-card.component.html',
  styleUrls: ['./change-staus-job-provider-notification-card.component.scss']
})
export class ChangeStausJobProviderNotificationCardComponent implements OnInit {

  @Input("notification") notification: Notification;


  constructor() { }

  ngOnInit(): void {
  }

}
