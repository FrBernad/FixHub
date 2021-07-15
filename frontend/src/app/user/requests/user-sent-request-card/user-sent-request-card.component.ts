import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {JobRequest} from "../../../models/job-request.model";
import {JobStatusEnum} from "../../../models/job-status-enum.model";
import {ContactService} from "../../../job/contact/contact.service";

@Component({
  selector: 'app-user-sent-request-card',
  templateUrl: './user-sent-request-card.component.html',
  styleUrls: ['./user-sent-request-card.component.scss']
})
export class UserSentRequestCardComponent implements OnInit {

  constructor(private contactService:ContactService) {
  }

  @ViewChild('accordion', {static: true}) accordion: ElementRef;
  @ViewChild('accordionBody', {static: true}) accordionBody: ElementRef;
  @Input() request: JobRequest;
  disabled = false;
  confirm = false;

  ngOnInit(): void {
    this.accordion.nativeElement.setAttribute('data-bs-target', '#collapse' + this.request.id);
    this.accordionBody.nativeElement.setAttribute('data-bs-parent', '#accordion' + this.request.id);
  }

  isWorkNotFinished() {
    return this.request.status == JobStatusEnum.PENDING || this.request.status== JobStatusEnum.IN_PROGRESS;
  }


  cancelRequest() {
    this.disabled = true;
    this.contactService.changeContactStatus(this.request.id, JobStatusEnum.CANCELED).subscribe(
      () => {
        this.disabled = false;
        this.request.status = JobStatusEnum.CANCELED;
      },
      () => {
        this.disabled = false;
      }
    );


  }
}
