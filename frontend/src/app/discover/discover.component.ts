import {Component, OnDestroy, OnInit} from '@angular/core';
import {OrderOption} from "../models/order-option-enum.model";
import {City, DiscoverService, JobPaginationQuery, JobPaginationResult, State} from "./discover.service";
import {Subscription} from "rxjs";
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.scss', '../shared/job-card/job-card.component.scss', '../auth/join/join.component.scss', '../landing-page/landing-page.component.scss']
})
export class DiscoverComponent implements OnInit, OnDestroy {

  jpr: JobPaginationResult = {
    results: [],
    page: 0,
    totalPages: 0,
  }

  jpq: JobPaginationQuery = {
    page: 0,
    pageSize: 6,
    order: OrderOption.MOST_POPULAR
  }

  searchError = false;

  cities: City[] = [];
  states: State[] = [];
  selectedState: string = ""
  selectedCity: string = ""
  isFetching = true;
  maxSearchInputLength: number = 50;

  orderOptions = Object.keys(OrderOption).filter((item) => {
    return isNaN(Number(item));
  });

  categories: string[] = [];

  private jobsSub: Subscription;

  constructor(
    private jobsService: DiscoverService,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {
    this.translateService.get("discover.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )

    const category = window.history.state['category'];
    const query = window.history.state['query'];
    if (category) {
      this.jpq.category = category;
    }

    if (query) {
      this.jpq.query = query;
    }

    this.jobsService.getJobs(this.jpq);
    this.jobsSub = this.jobsService.results.subscribe((results) => {
      this.jpr = {
        ...this.jpr,
        ...results
      };
      this.isFetching = false;
    });

    this.jobsService.getCategories().subscribe((categories) => {
      this.categories = categories.values;
    });

    this.jobsService.getStates().subscribe((states) => {
      this.states = states;
    });
  }

  onChangeCategory(category: string) {
    this.jpq.category = category;
    this.jpq.page = 0;
    if (!category) {
      delete this.jpq.category;
    }
    this.jobsService.getJobs(this.jpq)
  }

  onChangeOrder(order: string) {
    this.jpq.order = order;
    this.jpq.page = 0;
    this.jobsService.getJobs(this.jpq)
  }

  onChangeState(state: string, id: string) {
    this.selectedState = state;
    this.jpq.state = id;
    this.jpq.page = 0;
    if (!!state) {
      this.jobsService.getStateCities(id).subscribe((cities) => {
        this.cities = cities;
      });
    } else {
      delete this.jpq.state;
    }
    delete this.jpq.city;
    this.selectedCity = "";
    this.cities = [];
    this.jobsService.getJobs(this.jpq);
  }

  onChangeCity(city: string, id: string) {
    this.selectedCity = city;
    this.jpq.city = id;
    this.jpq.page = 0;
    if (!id) {
      delete this.jpq.city;
    }
    this.jobsService.getJobs(this.jpq);
  }

  onChangePage(page: number) {
    this.jpq.page = page;
    this.jobsService.getJobs(this.jpq);
  }

  onSearchEnter(e: KeyboardEvent, query: string) {
    if (e.key === "Enter") {
      this.onSearch(query);
    }
  }

  onSearch(query: string) {
    if (!this.searchError) {
      this.jpq.page = 0;
      this.jpq.query = query;
      this.jobsService.getJobs(this.jpq)
    }
  }

  checkLength(query: string) {
    this.searchError = query.length > 50;
  }

  ngOnDestroy(): void {
    this.jobsSub.unsubscribe();
  }

}



