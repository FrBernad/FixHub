import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../auth/user.service";
import {AuthService} from "../auth/auth.service";
import {PreviousRouteService} from "../auth/previous-route.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  showPass = true;
  recoverPass = false;
  loginForm: FormGroup;
  error = false;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private previousRouteService: PreviousRouteService
  ) {
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      'email': new FormControl(null, [Validators.required]),
      'password': new FormControl(null, [Validators.required]),
      'rememberMe': new FormControl(false)
    });

  }

  onSubmitLogin() {
    // if (!form.valid) {
    //   return;
    // }
    const email = this.loginForm.value.email;
    const password = this.loginForm.value.password;

    const authObs = this.authService.login(email, password);

    authObs.subscribe(
      () => {
        this.userService
          .populateUserData()
          .subscribe(() => {
            let url = '/user/profile';
            if (this.previousRouteService.getAuthRedirect()) {
              let prevUrl = this.previousRouteService.getPreviousUrl();
              url = !!prevUrl ? prevUrl : url;
              this.previousRouteService.setAuthRedirect(false);
            }
            this.router.navigate([url]);
          });
      },
      errorMessage => {
        console.log(errorMessage);
      }
    );

    // form.reset();
  }

  toogleEye() {
    this.showPass = !this.showPass;
  }

}
