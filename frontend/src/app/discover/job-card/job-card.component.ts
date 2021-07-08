import { Component, OnInit } from '@angular/core';
import {Job} from "../../models/Job";

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss','../../discover/discover.component.scss']
})
export class JobCardComponent implements OnInit {

  job:Job;

  constructor() { }

  ngOnInit(): void {
  }

}
