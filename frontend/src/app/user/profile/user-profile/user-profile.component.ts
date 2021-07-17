import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../auth/services/user.service";
import {User} from "../../../models/user.model";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  private userSub: Subscription;

  user: User;
  loggedUser: User;
  loading = true;
  disable = false;


  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private titleService: Title,
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
        this.titleService.setTitle('Fixhub | ' + user.name + " " + user.surname);
      },
      () => {
        this.router.navigate(['/404'])
      }
    );
  }

  unfollow() {
    this.disable = true;
    this.userService.unfollow(this.user.id).subscribe(() => {
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
