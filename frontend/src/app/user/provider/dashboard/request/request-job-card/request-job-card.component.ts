import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {ContactService, JobRequest} from "../../../../../job/contact/contact.service";
import {JobStatusModel} from "../../../../../models/job-status.model";

@Component({
  selector: 'app-request-job-card',
  templateUrl: './request-job-card.component.html',
  styleUrls: ['./request-job-card.component.scss', '../../dashboard.component.scss']
})
export class RequestJobCardComponent implements OnInit {

  @Input() request: JobRequest;

  @ViewChild('accordion', {static: true}) accordion: ElementRef;

  acceptJobLoading = false;
  rejectJobLoading=false;
  finishJobLoading=false;

  constructor(private contactService:ContactService) {
  }

  ngOnInit(): void {
    this.accordion.nativeElement.setAttribute('data-bs-target', '#accordion' + this.request.id);
  }

  isWorkInProgress():boolean{
    return this.request.status==JobStatusModel.IN_PROGRESS;
  }

  isWorkPending():boolean{
    return this.request.status==JobStatusModel.PENDING;
  }

  acceptJob():void{
    this.acceptJobLoading = true;
    this.contactService.changeContactStatus(this.request.id,JobStatusModel.IN_PROGRESS).subscribe(
      ()=>{
        this.acceptJobLoading = false;
        this.request.status=JobStatusModel.IN_PROGRESS;
      }
    );
  }
  rejectJob():void{
    this.rejectJobLoading=true;

    this.contactService.changeContactStatus(this.request.id,JobStatusModel.REJECTED).subscribe(
      ()=> {
        this.rejectJobLoading=false;
        this.request.status = JobStatusModel.REJECTED;
      }
    );
  }
  jobFinished():void{
    this.finishJobLoading=true;
    this.contactService.changeContactStatus(this.request.id,JobStatusModel.FINISHED).subscribe(
      ()=>{
        this.rejectJobLoading=false;
        this.request.status=JobStatusModel.FINISHED;
      }
    )
  }

}
