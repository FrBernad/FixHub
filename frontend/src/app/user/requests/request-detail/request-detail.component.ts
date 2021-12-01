import {Component, OnInit} from '@angular/core';
import {RequestsService} from "../requests.service";
import {ActivatedRoute, Router} from "@angular/router";
import {JobRequest} from "../../../models/job-request.model";
import {JobStatus} from "../../../models/job-status-enum.model";
import {Title} from "@angular/platform-browser";
import {User} from "../../../models/user.model";
import {UserService} from "../../../auth/services/user.service";
import {Observable} from "rxjs";

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
  error = false;
  confirm = false;
  disabled = false;
  user: User;
  requestType: string;

  constructor(
    private contactService: RequestsService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private titleService: Title,
  ) {
  }

  ngOnInit(): void {
    this.request.id = this.route.snapshot.params["id"]
    this.requestType = this.route.snapshot.data["type"];
    this.userService.user.subscribe(
      (res) => {
        this.user = res;
      }
    )
    let requestObs: Observable<JobRequest>;
    if (this.requestType === "sent") {
      requestObs = this.contactService.getSentJobRequest(this.request.id);
    } else {
      requestObs = this.contactService.getReceivedJobRequest(this.request.id);
    }

    requestObs.subscribe(
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
    return this.request.status == JobStatus.IN_PROGRESS;
  }

  isWorkPending(): boolean {
    return this.request.status == JobStatus.PENDING;
  }

  isWorkPendingOrInProgress() {
    return this.request.status == JobStatus.PENDING || this.request.status == JobStatus.IN_PROGRESS;
  }

  isWorkFinished() {
    return this.request.status == JobStatus.FINISHED;
  }

  isWorkRejected() {
    return this.request.status == JobStatus.REJECTED;
  }

  acceptJob(): void {
    this.acceptJobLoading = true;
    this.contactService.changeReceivedRequestStatus(this.request.id, JobStatus.IN_PROGRESS).subscribe(
      () => {
        this.acceptJobLoading = false;
        this.request.status = JobStatus.IN_PROGRESS;
      },
      () => {
        this.error = true;
        this.contactService.getReceivedJobRequest(this.request.id).subscribe(
          (req) => {
            this.acceptJobLoading = false;
            this.request = req;
            setTimeout(() => {
              this.error = false;
            }, 4000);
          },
          () => {
            this.acceptJobLoading = false;
            this.error = false;
          }
        );

      }
    );
  }

  rejectJob(): void {
    this.rejectJobLoading = true;
    this.contactService.changeReceivedRequestStatus(this.request.id, JobStatus.REJECTED).subscribe(
      () => {
        this.rejectJobLoading = false;
        this.request.status = JobStatus.REJECTED;
      },
      () => {
        this.error = true;
        this.contactService.getReceivedJobRequest(this.request.id).subscribe(
          (req) => {
            this.rejectJobLoading = false;
            this.request = req;
            setTimeout(() => {
              this.error = false;
            }, 4000);
          },
          () => {
            this.error = false;
            this.rejectJobLoading = false;
          }
        );
      }
    );
  }

  jobFinished(): void {
    this.finishJobLoading = true;
    this.contactService.changeReceivedRequestStatus(this.request.id, JobStatus.FINISHED).subscribe(
      () => {
        this.finishJobLoading = false;
        this.request.status = JobStatus.FINISHED;
      },
      () => {
        this.error = true;
        this.contactService.getReceivedJobRequest(this.request.id).subscribe(
          (req) => {
            this.finishJobLoading = false;
            this.request = req;
            setTimeout(() => {
              this.error = false;
            }, 4000);
          },
          () => {
            this.error = false;
            this.finishJobLoading = false;
          })
      }
    )
  }

  cancelSentRequest() {
    this.disabled = true;
    this.contactService.changeSentRequestStatus(this.request.id, JobStatus.CANCELED).subscribe(
      () => {
        this.disabled = false;
        this.request.status = JobStatus.CANCELED;
      },
      () => {
        this.error = true;
        this.contactService.getSentJobRequest(this.request.id).subscribe(
          (req) => {
            this.disabled = false;
            this.request = req;
            setTimeout(() => {
              this.error = false;
            }, 4000);
          },
          () => {
            this.error = false;
            this.disabled = false;
          })
      }
    );
  }

}
