import {Component, OnDestroy, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";
import {Subscription} from "rxjs";
import {AppComponent} from "../app.component";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {AuthService} from "../auth/auth.service";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../auth/user.service";

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
    // const email = form.value.email;
    // const password = form.value.password;

    const authObs = this.authService.login("pepe@yopmail.com", "123456");

    authObs.subscribe(
      () => {
        this.userService
          .populateUserData()
          .subscribe(() => {
            this.router.navigate(['/user/profile']);
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
