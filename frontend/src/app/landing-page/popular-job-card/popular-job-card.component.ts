import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../../models/Job";

@Component({
  selector: 'app-popular-job-card',
  templateUrl: './popular-job-card.component.html',
  styleUrls: ['./popular-job-card.component.scss','../../discover/discover.component.scss']
})
export class PopularJobCardComponent implements OnInit {

  @Input() job:Job;

  constructor() { }

  ngOnInit(): void {
  }

}
