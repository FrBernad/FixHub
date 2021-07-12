import { Component, Input, OnInit } from '@angular/core';
import {User} from "../models/user.model";
import {UserService} from "../auth/user.service";
import {Subscription} from "rxjs";
import {City, State} from "../discover/jobs.service";

export interface Schedule{
  startTime:string;
  endTime:string;
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
  schedule: Schedule;
  startTime;
  endTime;
  isProvider: boolean;


  constructor(
    private userService: UserService,
  ){ }


  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
      this.isProvider = this.user.providerDetails !== undefined;
    });
  }


  onChooseState(event){
    this.state = event.state;
    this.startTime = event.startTime;
    this.endTime = event.endTime;
    this.chooseState = false;
  }

  onChooseCities(event) {
    this.cities = event;
    if(this.isProvider){
      console.log('editando');
    }else{
      console.log('te hago provider')
    }
  }

}
