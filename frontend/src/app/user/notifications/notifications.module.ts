import {NgModule} from "@angular/core";
import {NotificationsComponent} from "./notifications.component";
import {SharedModule} from "../../shared/shared.module";
import {NotificationsRoutingModule} from "./notifications-routing.module";
import {NewFollowerNotificationCard} from './new-follower-notification-card/new-follower-notification-card.component';
import {NewJobRequestNotificationCardComponent} from './new-job-request-notification-card/new-job-request-notification-card.component';
import {ChangeStatusJobUserNotificationCardComponent} from './change-status-job-user-notification-card/change-status-job-user-notification-card.component';
import {ChangeStatusJobProviderNotificationCard} from "./change-status-job-provider-notification-card/change-status-job-provider-notification-card.component";

@NgModule({
  declarations: [
    NotificationsComponent,
    NewFollowerNotificationCard,
    NewJobRequestNotificationCardComponent,
    ChangeStatusJobUserNotificationCardComponent,
    ChangeStatusJobProviderNotificationCard
  ],
  imports: [
    SharedModule,
    NotificationsRoutingModule
  ],
  exports: [
    NotificationsComponent,
  ]
})
export class NotificationsModule {
}
