import {Component, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";
import {Subscription} from "rxjs";
import {AppComponent} from "../app.component";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  showPass = true;
  recoverPass = false;
  loginForm: FormGroup;
  error=false;


  constructor() {
  }

  ngOnInit(): void {
    this.loginForm= new FormGroup({
      'email': new FormControl(null, [Validators.required]),
      'password': new FormControl(null, [Validators.required]),
      'rememberMe': new FormControl(false)
    });

  }

  onSubmit() {

  }

  toogleEye() {
    this.showPass = !this.showPass;
  }

}
