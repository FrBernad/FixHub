import {Component, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";
import {Subscription} from "rxjs";
import {AppComponent} from "../app.component";
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  showPass = true;
  recoverPass = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  toogleEye() {
    this.showPass = !this.showPass;
  }

}
