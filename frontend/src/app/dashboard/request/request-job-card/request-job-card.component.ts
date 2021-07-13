import {Component, Input, OnInit} from '@angular/core';
import {JobRequest} from "../../../job/contact/contact.service";
import {FilterStatusRequestModel} from "../../../models/filterStatusRequest.model";

@Component({
  selector: 'app-request-job-card',
  templateUrl: './request-job-card.component.html',
  styleUrls: ['./request-job-card.component.scss','../../dashboard.component.scss']
})
export class RequestJobCardComponent implements OnInit {

  constructor() {
  }

  @Input() request: JobRequest;

  ngOnInit(): void {
  }



}
