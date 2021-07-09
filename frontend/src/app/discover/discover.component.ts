import {Component, OnInit} from '@angular/core';
import {User} from "../models/user.model";
import {Job} from "../models/job.model";
import {JobCategoryModel} from "../models/jobCategory.model";
import {OrderOptionModel} from "../models/orderOption.model";


@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.scss', './job-card/job-card.component.scss', '../join/join.component.scss', '../landing-page/landing-page.component.scss']
})
export class DiscoverComponent implements OnInit {

  provider: User = new User(1, "","","","","","","","",[]);

  orderOptions = Object.keys(OrderOptionModel).filter((item) => {
    return isNaN(Number(item));
  });

  categories = Object.keys(JobCategoryModel).filter((item) => {
    return isNaN(Number(item));
  });

  states = ['Mendoza', 'Buenos Aires', 'Salta'];
  cities = ['Buenos Aires', 'La Plata', 'Rosario'];


  job: Job = {
    id: 1, description: 'Armo Sillas', jobProvided: 'Sillas de roble',
    category: JobCategoryModel.CARPINTERO, price: 121, totalRatings: 0,
    averageRating: 0, images: [], reviews: [],
    provider: this.provider,
    paused: false,
    thumbnailId: 1
  };


  results = {
    results: [this.job, this.job, this.job],
    query: null,
    order: OrderOptionModel.MOST_POPULAR,
    category: JobCategoryModel.CARPINTERO,
    state: this.states[0],
    city: this.cities[0]
  }


  constructor() {
  }

  ngOnInit(): void {
  }

}



