import {Component, OnInit} from '@angular/core';
import {Job} from "../models/Job";
import {User} from "../models/User";

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
    profileImage: 1,
    coverImage: 2,
    following: [],
    followers: [],
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
    id: 1, description: 'asda', jobProvided: 'dasda',
    category: 'CARPINTERO', price: 121, totalRatings: 0,
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
