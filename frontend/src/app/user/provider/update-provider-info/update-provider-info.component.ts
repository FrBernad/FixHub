import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../auth/services/user.service";
import {Schedule} from "../../../auth/join/join.component";
import {City, State} from "../../../discover/discover.service";
import {User} from "../../../models/user.model";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-update-provider-info',
  templateUrl: './update-provider-info.component.html',
  styleUrls: ['./update-provider-info.component.scss', '../../../auth/join/join.component.scss']
})
export class UpdateProviderInfoComponent implements OnInit {

  private userSub: Subscription;
  user: User;
  chooseState = true;
  state: State;
  cities: City[];
  schedule: Schedule;
  startTime: string;
  endTime: string;
  isProvider: boolean;
  posting = false;


  constructor(
    private userService: UserService,
    private router: Router,
    private titleService: Title,
    private translateService: TranslateService
  ) {
  }

  ngOnInit(): void {

    this.changeTitle();

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });

    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
      this.isProvider = this.user.providerDetails !== undefined;
    });
  }

  changeTitle() {
    this.translateService.get("updateProviderInfo.title")
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

  updateProviderInfo(event: City[]) {
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
    this.userService.updateProviderInfo(providerInfo).subscribe(
      () => {
        this.posting = false;
        this.router.navigate(['user', 'dashboard']);
      }
    );


  }


}
