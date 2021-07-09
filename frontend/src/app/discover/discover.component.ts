import {Component, OnInit} from '@angular/core';
import {User} from "../models/user.model";
import {Job} from "../models/job.model";
import {JobCategoryModel} from "../models/jobCategory.model";
import {OrderOptionModel} from "../models/orderOption.model";


@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.scss', '/job-card/job-card.component.scss','../join/join.component.scss','../landing-page/landing-page.component.scss']
})
export class DiscoverComponent implements OnInit {

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

    orderOptions = Object.keys(OrderOptionModel).filter((item) => {
    return isNaN(Number(item));});

    categories = Object.keys(JobCategoryModel).filter((item) => {
    return isNaN(Number(item));});


  job: Job = {
    id: 1, description: 'Armo Sillas', jobProvided: 'Sillas de roble',
    category: JobCategoryModel.CARPINTERO, price: 121, totalRatings: 0,
    averageRating: 0, images: [], reviews: [],
    provider: this.provider,
    paused: false,
    thumbnailId: 1
  };

  states= ['Mendoza','Buenos Aires','Salta'];
  cities = ['Buenos Aires', 'La Plata', 'Rosario'];



  results= {
    results: [this.job,this.job,this.job],
    query:null,
    order:this.orderOptions[0],
    category:this.categories[0],
    state:this.states[0],
    city: this.cities[0]
  }


  constructor() { }

  ngOnInit(): void {
  }

}



