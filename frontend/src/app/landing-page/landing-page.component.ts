import {Component, OnDestroy, OnInit} from '@angular/core';
import {animate, query, stagger, state, style, transition, trigger} from "@angular/animations";
import {User} from "../models/user.model";
import {DiscoverService} from "../discover/discover.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {UserService} from "../auth/services/user.service";
import {Title} from "@angular/platform-browser";

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
export class LandingPageComponent implements OnInit, OnDestroy {

  loading = true;

  searchError = false;

  categories: string[] = [];

  popularJobs = [];

  jobsSub: Subscription;
  userSub: Subscription;
  searchOptionsSub: Subscription;

  user: User;
  isFetching = true;
  maxSearchInputLength: number = 50;

  constructor(
    private jobsService: DiscoverService,
    private userService: UserService,
    private router: Router,
    private titleService: Title,
  ) {
  }

  ngOnInit(): void {
    this.jobsService.getJobs({page: 0});

    this.titleService.setTitle('Fixhub');

    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    });

    this.jobsService.getSearchOptions();

    this.searchOptionsSub = this.jobsService.searchOptions.subscribe((searchOptions) => {
      if (!!searchOptions) {
        this.categories = searchOptions.find((option) => {
          return option.key === "categories"
        }).values.slice(0, 5);
        this.loading = false;
      }
    });

    this.jobsSub = this.jobsService.results.subscribe((results) => {
      this.popularJobs = results.results;
      this.isFetching = false;
    }, () => {
      this.isFetching = false;
    });

  }

  onChangeCategory(category: string) {
    this.router.navigate(['/discover'],
      {
        queryParams: {
          category,
          page: '0',
          pageSize: '6',
          order: 'MOST_POPULAR'
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
          queryParams: {
            query,
            page: '0',
            pageSize: '6',
            order: 'MOST_POPULAR'
          }
        });
    }
  }

  checkLength(query: string) {
    this.searchError = query.length > 50;
  }

  ngOnDestroy(): void {
    this.jobsSub.unsubscribe();
    this.userSub.unsubscribe();
    this.searchOptionsSub.unsubscribe();
  }

}
