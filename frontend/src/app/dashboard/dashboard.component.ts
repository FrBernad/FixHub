import {Component, OnInit} from '@angular/core';
import {OrderOptionModel} from '../models/orderOption.model';
import {JobPaginationQuery, JobPaginationResult, JobsService} from "../discover/jobs.service";
import {Job} from "../models/job.model";
import {ProviderDetails, User} from "../models/user.model";
import {JobCategoryModel} from "../models/jobCategory.model";
import {UserService} from "../auth/user.service";
import {Contact} from "../job/contact/contact.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  providerDetails: ProviderDetails = {
    location: {
      cities: [{id: 1, name: 'Burzaco'}, {id: 2, name: 'Beriso'}],
      state: {id: 1, name: 'Buenos Aires'}
    },
    schedule: {
      startTime: new Date(),
      endTime: new Date()
    },
    jobsCount:20,
    avgRating:4,
    reviewCount:35
  };

  provider = new User(
    1,
    'Agus',
    'Manfredi',
    'agus@yopmail.com',
    '+5491112345678',
    'Buenos Aires',
    'Adrogue',
    '',
    '',
    ['PROVIDER', 'VERIFIED'], this.providerDetails);

  job: Job = {
    id: 1,
    description: 'Armo Sillas',
    jobProvided: 'Sillas de roble',
    category: JobCategoryModel.CARPINTERO,
    price: 121,
    totalRatings: 0,
    averageRating: 0,
    images: [],
    provider: this.provider,
    paused: false,
    thumbnailImage: ""
  };

  contactAux = new Contact('State', 'City', 'Street', 120, 1, 'C', 'I need the job',new Date());

  contacts:Contact[] = [this.contactAux,this.contactAux,this.contactAux];

  jpr: JobPaginationResult = {
    results: [this.job, this.job, this.job, this.job, this.job],
    page: 0,
    totalPages: 4,
  };

  jpq: JobPaginationQuery = {
    page: 0,
    pageSize: 4,
    order: OrderOptionModel.MOST_POPULAR
  };

  activeTab: string = 'dashboard';

  orderOptions = Object.keys(OrderOptionModel).filter((item) => {
    return isNaN(Number(item));
  });

  private userSub: Subscription;
  user: User;

  constructor(
    private jobsService: JobsService,
    private userService: UserService) {
  }

  ngOnInit(): void {
    this.jobsService.getJobs(this.jpq);
    // this.userSub = this.userService.user.subscribe(user =>{
    //   this.user = user;
    // })
  }

  changeTab(tab: string) {
    this.activeTab = tab;
  }

  getNumberOfWorks(): number{
    return this.provider.providerDetails.jobsCount;
  }

  getAvgRating(): number{
    return this.provider.providerDetails.avgRating;
  }
  getNumberOfReviews(): number{
    return this.provider.providerDetails.reviewCount;
  }

  getNumberOfJobRequest():number{
    return 4;
  }

  onChangeOrder(order:string){
    this.jpq.order=order;
    this.jobsService.getJobs(this.jpq);
  }

  onChangePage(page: number) {
    this.jpq.page = page;
    this.jobsService.getJobs(this.jpq)
  }

}
