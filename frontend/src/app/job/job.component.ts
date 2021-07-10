import {Component, OnInit} from '@angular/core';
import {Job} from "../models/job.model";
import {User} from "../models/user.model";
import {ActivatedRoute, Params} from "@angular/router";
import {JobService} from "./job.service";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit {

  job: Job = new Job();

  loggedUser: User;
  canReview: boolean;
  selectedIndex = 0;
  isFetching = true;

  results = {
    totalPages: 0
  };
  firstResults: {
    results: []
  };

  constructor(private route: ActivatedRoute, private jobService: JobService) {
  }

  selectPrevious(){
    if(this.selectedIndex == 0){
      this.selectedIndex = this.job.images.length-1;
    }else {
      this.selectedIndex--;
    }
  }

  selectNext(){
    if(this.selectedIndex == this.job.images.length-1){
      this.selectedIndex = 0;
    }else {
      this.selectedIndex++;
    }
  }

  getStartTime() {
    // let startTime = this.job.provider.providerDetails.schedule.startTime;
    // return startTime.getHours() + ':' + startTime.getMinutes();
    return '1'
  }

  getEndTime() {
    // let endTime = this.job.provider.providerDetails.schedule.endTime;
    // return endTime.getHours() + ':' + endTime.getMinutes();
    return '2';
  }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.job.id = params['jobId'];
      }
    );

    this.jobService.getJob(+this.job.id).subscribe(
      responseData => {
        console.log(responseData.id);
        this.job.jobProvided = responseData.jobProvided;
        this.job.description = responseData.description;
        this.job.category = responseData.category;
        this.job.price = responseData.price;
        this.job.provider = responseData.provider;
        this.job.totalRatings = responseData.totalRatings;
        this.job.averageRating = responseData.averageRating;
        this.job.images = responseData.images;
        this.job.reviews = [];
        this.job.paused = responseData.paused;
        this.job.thumbnailId = 1;
        this.isFetching = false;
      }
    );
  }


}
