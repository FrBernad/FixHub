import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {JobRequest} from "../../job/contact/contact.service";

@Component({
  selector: 'app-user-sent-request-card',
  templateUrl: './user-sent-request-card.component.html',
  styleUrls: ['./user-sent-request-card.component.scss']
})
export class UserSentRequestCardComponent implements OnInit {

  constructor() {
  }

  @ViewChild('accordion', {static: true}) accordion: ElementRef;
  @ViewChild('accordionBody', {static: true}) accordionBody: ElementRef;
  @Input() request: JobRequest;

  ngOnInit(): void {
    this.accordion.nativeElement.setAttribute('data-bs-target', '#collapse' + this.request.id);
    this.accordionBody.nativeElement.setAttribute('data-bs-parent', '#accordion' + this.request.id);
  }

}
