import {Component, OnInit} from '@angular/core';
import {ContactService} from "../../../job/contact/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {JobRequest} from "../../../models/job-request.model";
import {JobStatusEnum} from "../../../models/job-status-enum.model";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-request',
  templateUrl: './request-detail.component.html',
  styleUrls: ['./request-detail.component.scss']
})
export class RequestComponent implements OnInit {

  loading = true;
  request: JobRequest = new JobRequest;

  acceptJobLoading = false;
  rejectJobLoading = false;
  finishJobLoading = false;


  constructor(
    private contactService: ContactService,
    private route: ActivatedRoute,
    private router: Router,
    private titleService: Title,
  ) {
  }

  ngOnInit(): void {
    this.request.id = this.route.snapshot.params["id"]
    this.contactService.getProviderJobRequest(this.request.id).subscribe(
      request => {
        this.request = request;
        this.loading = false;
        this.titleService.setTitle('Fixhub | ' + request.jobProvided)
      },
      () => {
        this.router.navigate(['/404']);
      }
    );
  }

  isWorkInProgress(): boolean {
    return this.request.status == JobStatusEnum.IN_PROGRESS;
  }

  isWorkPending(): boolean {
    return this.request.status == JobStatusEnum.PENDING;
  }

  acceptJob(): void {
    this.acceptJobLoading = true;
    this.contactService.changeContactStatus(this.request.id, JobStatusEnum.IN_PROGRESS).subscribe(
      () => {
        this.acceptJobLoading = false;
        this.request.status = JobStatusEnum.IN_PROGRESS;
      },
      () => {
        this.acceptJobLoading = false;
      }
    );
  }

  rejectJob(): void {
    this.rejectJobLoading = true;
    this.contactService.changeContactStatus(this.request.id, JobStatusEnum.REJECTED).subscribe(
      () => {
        this.rejectJobLoading = false;
        this.request.status = JobStatusEnum.REJECTED;
      },
      () => {
        this.rejectJobLoading = false;
      }
    );
  }

  jobFinished(): void {
    this.finishJobLoading = true;
    this.contactService.changeContactStatus(this.request.id, JobStatusEnum.FINISHED).subscribe(
      () => {
        this.rejectJobLoading = false;
        this.request.status = JobStatusEnum.FINISHED;
      },
      () => {
        this.finishJobLoading = false;
      }
    )
  }

}
