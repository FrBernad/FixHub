import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-join',
  templateUrl: './join.component.html',
  styleUrls: ['./join.component.scss']
})
export class JoinComponent implements OnInit {

  constructor() { }

  chooseState = true;

  @Input()
  public isEdit: boolean;
  
  ngOnInit(): void {
  }

}
