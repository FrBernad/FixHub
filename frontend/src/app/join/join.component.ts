import {Component, Input, OnInit} from '@angular/core';
import {User} from "../models/user.model";
import {UserService} from "../auth/user.service";
import {Subscription} from "rxjs";
import {City, State} from "../discover/jobs.service";
import {Router} from "@angular/router";

export interface Schedule {
  startTime: string;
  endTime: string;
}

@Component({
  selector: 'app-join',
  templateUrl: './join.component.html',
  styleUrls: ['./join.component.scss']
})
export class JoinComponent implements OnInit {

  private userSub: Subscription;
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
    private router: Router
  ) {
  }


  ngOnInit(): void {

    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
      this.isProvider = this.user.providerDetails !== undefined;
    });
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
    this.userService.makeProvider(providerInfo).subscribe(
      () => {
        this.posting = false;
        this.router.navigate(['user', 'dashboard']);
      }
    );
  }

}
