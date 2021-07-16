import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {AuthService} from '../services/auth.service';
import {PreviousRouteService} from '../services/previous-route.service';
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  showPass1 = true;
  showPass2 = true;

  registerForm: FormGroup;

  maxNameLength: number = 50;
  maxSurnameLength: number = 50;
  maxEmailLength: number = 200;
  minPasswordLength: number = 6;
  maxPasswordLength: number = 20;
  maxPhoneNumberLength: number = 50;
  maxStateLength: number = 50;
  maxCityLength: number = 50;

  disable = false;
  error = false;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private previousRouteService: PreviousRouteService,
    private router: Router,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {
    this.translateService.get("register.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )

    this.registerForm = new FormGroup({
      name: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxNameLength),
        Validators.pattern(
          "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$"
        ),
      ]),
      surname: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxSurnameLength),
        Validators.pattern(
          "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$"
        ),
      ]),
      email: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxEmailLength),
        Validators.pattern('^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$'),
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(this.minPasswordLength),
        Validators.maxLength(this.maxPasswordLength),
      ]),
      confirmPassword: new FormControl(null, [
        Validators.required,
        Validators.minLength(this.minPasswordLength),
        Validators.maxLength(this.maxPasswordLength),
      ]),
      phoneNumber: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxPhoneNumberLength),
        Validators.pattern(
          '^[+]?(?:(?:00)?549?)?0?(?:11|15|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$'
        ),
      ]),
      state: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxStateLength),
        Validators.pattern(
          "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
        ),
      ]),
      city: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxCityLength),
        Validators.pattern(
          "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
        ),
      ]),
    });
    this.registerForm.setValidators(this.passwordMatching.bind(this));

  }

  passwordMatching(group: FormGroup): { [s: string]: boolean } {
    const confirmPasswordControl = group.controls['confirmPassword'];
    if (group.controls['password'].value != confirmPasswordControl.value) {
      confirmPasswordControl.setErrors({passwordsDontMatch: true});
    } else {
      confirmPasswordControl.setErrors(null);
    }
    return;
  }

  toogleEye1() {
    this.showPass1 = !this.showPass1;
  }

  toogleEye2() {
    this.showPass2 = !this.showPass2;
  }

  onSubmit() {
    this.disable = true;

    if (!this.registerForm.valid) {
      this.registerForm.markAllAsTouched();
      this.disable = false;
      return;
    }


    const registerData = {
      name: this.registerForm.value.name,
      surname: this.registerForm.value.surname,
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
      phoneNumber: this.registerForm.value.phoneNumber,
      state: this.registerForm.value.state,
      city: this.registerForm.value.city,
    };


    const authObs = this.authService.signup(registerData);

    authObs.subscribe(
      () => {
        this.authService
          .login(registerData.email, registerData.password)
          .subscribe(
            () => {
              this.userService.populateUserData().subscribe(() => {
                this.disable = false;
                let url = '/user/profile';
                this.router.navigate([url]);
              });
            },
            () => {
              this.disable = false;
            }
          );
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
}
