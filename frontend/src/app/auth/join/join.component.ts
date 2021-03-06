import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../models/user.model";
import {UserService} from "../services/user.service";
import {Subscription} from "rxjs";
import {City, State} from "../../discover/discover.service";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

export interface Schedule {
  startTime: string;
  endTime: string;
}

@Component({
  selector: 'app-join',
  templateUrl: './join.component.html',
  styleUrls: ['./join.component.scss']
})
export class JoinComponent implements OnInit, OnDestroy {

  private userSub: Subscription;
  private transSub: Subscription;
  user: User;
  chooseState = true;
  state: State;
  cities: City[];
  startTime: string;
  endTime: string;
  isProvider: boolean;
  posting = false;


  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {

    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
      this.isProvider = this.user.providerDetails !== undefined;
    });

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });
  }

  changeTitle() {
    this.translateService.get("join.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }


  onChooseState(event) {
    this.state = event.state;
    this.startTime = event.startTime;
    this.endTime = event.endTime;
    this.chooseState = false;
  }

  makeProvider(event: City[]) {
    this.cities = event;
    this.posting = true;
    let providerInfo = {
      schedule: {
        startTime: this.startTime,
        endTime: this.endTime
      },
      location: {
        state: this.state,
        cities: this.cities
      }
    };
    this.authService.makeProvider(providerInfo).subscribe(
      () => {
        this.posting = false;
        this.router.navigate(['/user/dashboard']);
      }, () => {
        this.router.navigate(['/user/profile']);
      }
    );
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
    this.transSub.unsubscribe();
  }

}
