import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../auth/user.service";
import {User} from "../../models/user.model";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  user: User;
  loggedUser: User;
  loading = true;
  disable = false;

  userSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
  ) {
  }

  ngOnInit(): void {
    const userId = this.route.snapshot.params['id'];
    this.userSub = this.userService.user.subscribe((user) => {
      this.loggedUser = user;
    })
    this.userService.getUser(userId).subscribe(
      (user) => {
        this.user = user;
        this.loading = false;
      }
    );
  }

  unfollow() {
    this.disable = true;
    this.userService.unfollow(this.user.id).subscribe(() => {
      this.userService.populateUserData();
      this.userService.getUser(this.user.id).subscribe(
        (user) => {
          this.user = user;
          this.disable = false;
        }
      );
    });
  }

  follow() {
    this.disable = true;
    this.userService.follow(this.user.id).subscribe(() => {
      this.userService.populateUserData();
      this.userService.getUser(this.user.id).subscribe(
        (user) => {
          this.user = user;
          this.disable = false;
        }
      );
    });
  }

  ngOnDestroy(): void {
    if (this.userSub) {
      this.userSub.unsubscribe();
    }
  }

}