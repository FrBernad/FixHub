import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../services/user.service";
import {AuthService} from "../services/auth.service";
import {PreviousRouteService} from "../services/previous-route.service";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  showPass = true;
  loginForm: FormGroup;
  error = false;
  disable = false;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private previousRouteService: PreviousRouteService,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      'email': new FormControl(null, [Validators.required]),
      'password': new FormControl(null, [Validators.required]),
    });

    this.changeTitle();

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });
  }

  changeTitle() {
    this.translateService.get("login.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  onSubmitLogin() {
    this.disable = true;
    if (!this.loginForm.valid) {
      this.loginForm.markAllAsTouched();
      this.disable = false;
      return;
    }
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
            this.disable = false;
          });
      },
      () => {
        this.disable = false;
        this.error = true;
        setTimeout(() => {
          this.error = false
        }, 4000)
      }
    );
  }

  toogleEye() {
    this.showPass = !this.showPass;
  }

}
