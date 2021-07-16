import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ContactService} from "../../../../../job/contact/contact.service";
import {JobStatusEnum} from "../../../../../models/job-status-enum.model";
import {JobRequest} from "../../../../../models/job-request.model";

@Component({
  selector: 'app-request-job-card',
  templateUrl: './request-job-card.component.html',
  styleUrls: ['./request-job-card.component.scss', '../../dashboard.component.scss']
})
export class RequestJobCardComponent implements OnInit {

  @Input() request: JobRequest;

  @Output("changeStatus") changeStatus = new EventEmitter<void>()

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
    return this.request.status==JobStatusEnum.IN_PROGRESS;
  }

  isWorkPending():boolean{
    return this.request.status==JobStatusEnum.PENDING;
  }

  acceptJob():void{
    this.acceptJobLoading = true;
    this.contactService.changeContactStatus(this.request.id,JobStatusEnum.IN_PROGRESS).subscribe(
      ()=>{
        this.acceptJobLoading = false;
        this.request.status=JobStatusEnum.IN_PROGRESS;
      },
      ()=>{
        this.acceptJobLoading=false;
      }
    );
  }

  rejectJob():void{
    this.rejectJobLoading=true;
    this.contactService.changeContactStatus(this.request.id,JobStatusEnum.REJECTED).subscribe(
      ()=> {
        this.rejectJobLoading=false;
        this.request.status = JobStatusEnum.REJECTED;
      },
      ()=>{
        this.rejectJobLoading=false;
      }
    );
  }

  jobFinished():void{
    this.finishJobLoading=true;
    this.contactService.changeContactStatus(this.request.id,JobStatusEnum.FINISHED).subscribe(
      ()=>{
        this.rejectJobLoading=false;
        this.request.status=JobStatusEnum.FINISHED;
      },
      ()=>{
        this.finishJobLoading=false;
      }
    )
  }

}
