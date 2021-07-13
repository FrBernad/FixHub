import {Component, Input, OnInit} from '@angular/core';
import {JobRequest} from "../../../../job/contact/contact.service";

@Component({
  selector: 'app-user-sent-request-card',
  templateUrl: './user-sent-request-card.component.html',
  styleUrls: ['./user-sent-request-card.component.scss','../../../../dashboard/dashboard.component.scss']
})
export class UserSentRequestCardComponent implements OnInit {

  constructor() { }

  @Input() request:JobRequest;

  isAccordionOpen:boolean;

  toggleAccordion(){
    this.isAccordionOpen=!this.isAccordionOpen;
  }

  isOpen():boolean{
    return this.isAccordionOpen
  }

  ngOnInit(): void {
    this.isAccordionOpen=false;
  }

}
