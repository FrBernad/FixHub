import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {JobRequest} from "../../../models/job-request.model";

@Component({
  selector: 'app-request-card',
  templateUrl: './request-card.component.html',
  styleUrls: ['./request-card.component.scss']
})
export class RequestCardComponent implements OnInit {

  @ViewChild('accordion', {static: true}) accordion: ElementRef;

  @ViewChild('accordionBody', {static: true}) accordionBody: ElementRef;

  @Input() request: JobRequest;

  @Input() type: String;

  @Input() requestType: String;

  disabled = false;
  confirm = false;

  ngOnInit(): void {
    this.accordion.nativeElement.setAttribute('data-bs-target', '#collapse' + this.request.id);
    this.accordionBody.nativeElement.setAttribute('data-bs-parent', '#accordion' + this.request.id);
  }

}
