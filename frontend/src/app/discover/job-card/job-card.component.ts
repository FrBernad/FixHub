import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../../models/job.model";
import {JobCategoryModel} from "../../models/jobCategory.model";

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss','../../discover/discover.component.scss']
})
export class JobCardComponent implements OnInit {

  @Input()job:Job;

  constructor() { }

  ngOnInit(): void {
  }

}
