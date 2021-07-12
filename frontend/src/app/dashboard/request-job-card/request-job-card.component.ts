import {Component, Input, OnInit} from '@angular/core';
import {Contact} from "../../job/contact/contact.model";
import {Job} from "../../models/job.model";

@Component({
  selector: 'app-request-job-card',
  templateUrl: './request-job-card.component.html',
  styleUrls: ['./request-job-card.component.scss']
})
export class RequestJobCardComponent implements OnInit {

  constructor() { }

  @Input() contact:Contact;
  @Input() job:Job;

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
