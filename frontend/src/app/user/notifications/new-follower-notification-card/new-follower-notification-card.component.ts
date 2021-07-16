import {Component, Input, OnInit} from '@angular/core';
import {Notification} from "../../../models/notification.model";
import {UserService} from "../../../auth/services/user.service";
import {User} from "../../../models/user.model";
import {Router} from "@angular/router";
import {NotificationsService} from "../notifications.service";

@Component({
  selector: 'app-new-follower-notification-card',
  templateUrl: './new-follower-notification-card.component.html',
  styleUrls: ['./new-follower-notification-card.component.scss']
})
export class NewFollowerNotificationCard implements OnInit {

  @Input("notification") notification: Notification;
  isLoading = false;
  user:User;

  constructor(private userService: UserService,
              private router: Router,
              private notificationService: NotificationsService) {
  }

  ngOnInit(): void {
    this.isLoading = true;

    this.userService.getUser(this.notification.resource).subscribe(
      (responseData) => {
        this.isLoading = false;
        this.user = responseData;
      }
    );
  }


  onClick() {
    this.notificationService.markAsReadNotification(this.notification.id).subscribe(
      () => {
        this.notification.seen=true;}
    );
    this.router.navigate(['/user',this.user.id]);
  }
}
