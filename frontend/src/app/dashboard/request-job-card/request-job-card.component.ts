import {Component, Input, OnInit} from '@angular/core';
import {Contact} from "../../job/contact/contact.model";

@Component({
  selector: 'app-request-job-card',
  templateUrl: './request-job-card.component.html',
  styleUrls: ['./request-job-card.component.scss','../dashboard.component.scss']
})
export class RequestJobCardComponent implements OnInit {

  constructor() { }

  @Input() contact:Contact;

    ngOnInit(): void {
    }

}
