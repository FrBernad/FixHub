import {Component, OnInit} from '@angular/core';
import {Job} from "../models/job.model";
import {User} from "../models/user.model";
import {JobCategoryModel} from "../models/jobCategory.model";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit {

  provider: User = {
    id: 2,
    name: 'Agus',
    surname: 'Manfredi',
    email: 'agus@yopmail.com',
    phoneNumber: '+5491112345678',
    state: 'Buenos Aires',
    city: 'Adrogue',
    profileImage: "",
    coverImage: "",
    following: [],
    followers: [],
    roles: [],
    providerDetails: {
      location: {
        cities: [{id: 1, name: 'Burzaco'}],
        state: {id: 1, name: 'Buenos Aires'}
      },
      schedule: {
        startTime: new Date(),
        endTime: new Date()
      }
    },
  }

  job: Job = {
    id: 1, description: 'Armo sillas', jobProvided: 'Sillas de Roble',
    category: JobCategoryModel.CARPINTERO, price: 121, totalRatings: 0,
    averageRating: 0, images: [], reviews: [],
    provider: this.provider,
    paused: false,
    thumbnailId: 1
  };
  loggedUser: User;
  canReview: boolean;

  results = {
    totalPages: 0
  };
  firstResults: {
    results: []
  };

  constructor() {
  }

  getStartTime() {
    let startTime = this.job.provider.providerDetails.schedule.startTime;
    return startTime.getHours() + ':' + startTime.getMinutes();
  }

  getEndTime() {
    let endTime = this.job.provider.providerDetails.schedule.endTime;
    return endTime.getHours() + ':' + endTime.getMinutes();
  }

  ngOnInit(): void {
  }

}
