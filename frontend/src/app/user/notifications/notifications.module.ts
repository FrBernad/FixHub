import {NgModule} from "@angular/core";
import {NotificationsComponent} from "./notifications.component";
import {SharedModule} from "../../shared/shared.module";
import {NotificationsRoutingModule} from "./notifications-routing.module";
import { NotificationCardComponent } from './notification-card/notification-card.component';

@NgModule({
  declarations: [
    NotificationsComponent,
    NotificationCardComponent,
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
