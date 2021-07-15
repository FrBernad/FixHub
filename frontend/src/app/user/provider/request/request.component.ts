import {Component, OnInit} from '@angular/core';
import {ContactService} from "../../../job/contact/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Job} from "../../../models/job.model";
import {JobRequest} from "../../../models/job-request.model";

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.scss']
})
export class RequestComponent implements OnInit {

  loading = true;
  request: JobRequest = new JobRequest;

  constructor(
    private contactService: ContactService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.request.id = this.route.snapshot.params["id"]

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

}
