import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {JobRequest} from "../../../job/contact/contact.service";
import {FilterStatusRequestModel} from "../../../models/filterStatusRequest.model";

@Component({
  selector: 'app-request-job-card',
  templateUrl: './request-job-card.component.html',
  styleUrls: ['./request-job-card.component.scss', '../../dashboard.component.scss']
})
export class RequestJobCardComponent implements OnInit {

  @Input() request: JobRequest;

  @ViewChild('accordion', {static: true}) backNavbar: ElementRef;

  constructor() {
  }

  ngOnInit(): void {
    this.backNavbar.nativeElement.setAttribute('data-bs-target', '#accordion'+this.request.id);
  }

}
