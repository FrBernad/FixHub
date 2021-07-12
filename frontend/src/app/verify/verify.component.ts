import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../auth/auth.service";
import {UserService} from "../auth/user.service";

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.scss']
})
export class VerifyComponent implements OnInit {

  loading = true;
  success = false;

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService,
  ) {
  }

  ngOnInit(): void {
    const token = this.route.snapshot.params['token'];

    this.authService.verify(token).subscribe(() => {
      this.userService.populateUserData().subscribe(() => {
        this.success = true;
        this.loading = false;
      });
    }, () => {
      this.loading = false;
    })

  }

}
