import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../models/user.model";

@Component({
  selector: 'app-follower-card',
  templateUrl: './follower-card.component.html',
  styleUrls: ['./follower-card.component.scss']
})
export class FollowerCardComponent implements OnInit {

  @Input("follower") follower: User;

  constructor() { }

  ngOnInit(): void {
  }

}
