import {Component, OnInit} from '@angular/core';
import {animate, query, stagger, state, style, transition, trigger} from "@angular/animations";
import {User} from "../models/user.model";
import {Job} from "../models/job.model";
import {JobCategoryModel} from "../models/jobCategory.model";
import {JobsService} from "../discover/jobs.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss'],
  animations: [
    trigger('fadeIn', [
      transition('void => *', [
        state('in', style({
          transform: 'translateX(0)', opacity: 1
        })),
        query('.slogan, .searchBar, .categoryBtn', [
          style({
            transform: 'translateX(0)', opacity: 0
          }),
          stagger('0.3s', [
            style({
              transform: 'translateX(-100px)',
              opacity: 0
            }),
            animate('0.5s 0.1s ease')
          ])
        ]),
      ]),
    ])
  ]
})
export class LandingPageComponent implements OnInit {

  //FIXME: BORRAR TODA ESTA PARTE DE JOB Y USER
  provider: User = new User(1, "", "", "", "", "", "", "", "", []);

  job: Job = {
    id: 1, description: 'sillas de roble o pino', jobProvided: 'Arreglo sillas',
    category: JobCategoryModel.CARPINTERO, price: 121, totalRatings: 0,
    averageRating: 0, images: [], reviews: [],
    provider: this.provider,
    paused: false,
    thumbnailImage: ''
  };

  jobs = [
    this.job, this.job, this.job, this.job
  ];

  loading = true;

  searchError = false;

  categories: string[] = [];

  constructor(
    private jobsService: JobsService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.jobsService.getCategories().subscribe((categories) => {
      this.categories = categories.values.slice(0, 5);
      this.loading = false;
    });
  }

  onChangeCategory(category: string) {
    this.router.navigate(['/discover'],
      {
        state: {
          category
        }
      });
  }

  onSearchEnter(e: KeyboardEvent, query: string) {
    if (e.key === "Enter") {
      this.onSearch(query);
    }
  }

  onSearch(query: string) {
    if (!this.searchError) {
      this.router.navigate(['/discover'],
        {
          state: {
            query
          }
        });
    }
  }

  checkLength(query: string) {
    this.searchError = query.length > 50;
  }

}
