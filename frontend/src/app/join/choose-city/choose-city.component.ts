import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-choose-city',
  templateUrl: './choose-city.component.html',
  styleUrls: ['./choose-city.component.scss']
})
export class ChooseCityComponent implements OnInit {

  cities=[];

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() {

  }
}
