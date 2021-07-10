import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../auth/user.service";
import {Subscription} from "rxjs";
import {User} from "../models/user.model";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy {

  private userSub: Subscription;

  loading = true;
  user: User;

  constructor(
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.loading = false;
      this.user = user;
    });
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe()
  }

}
