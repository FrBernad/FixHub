import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-choose-state',
  templateUrl: './choose-state.component.html',
  styleUrls: ['./choose-state.component.scss']
})
export class ChooseStateComponent implements OnInit {

  chooseStateForm: FormGroup;
  states = [];

  constructor() { }

  ngOnInit(): void {
    this.chooseStateForm = new FormGroup({

    });
  }

  onSubmit(){

  }

}
