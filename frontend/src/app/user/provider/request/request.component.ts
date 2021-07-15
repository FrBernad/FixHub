import {Component, OnDestroy, OnInit} from '@angular/core';
import {ContactService} from "../../../job/contact/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Job} from "../../../models/job.model";
import {JobRequest} from "../../../models/job-request.model";
import {Subscription} from "rxjs";
import {UserService} from "../../../auth/services/user.service";
import {User} from "../../../models/user.model";
import {JobStatusEnum} from "../../../models/job-status-enum.model";

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.scss']
})
export class RequestComponent implements OnInit, OnDestroy {

  loading = true;
  request: JobRequest = new JobRequest;
  user: User;

  acceptJobLoading = false;
  rejectJobLoading = false;
  finishJobLoading = false;

  private userSub: Subscription;

  constructor(
    private contactService: ContactService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.request.id = this.route.snapshot.params["id"]
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    });
    this.contactService.getProviderJobRequest(this.request.id).subscribe(
      request => {
        this.request = request;
        this.loading = false;
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

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }


}
