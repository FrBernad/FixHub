import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {OrderOption} from "../models/order-option-enum.model";
import {City, DiscoverService, JobPaginationQuery, JobPaginationResult, State} from "./discover.service";
import {Subscription} from "rxjs";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.scss', '../shared/job-card/job-card.component.scss', '../auth/join/join.component.scss', '../landing-page/landing-page.component.scss']
})
export class DiscoverComponent implements OnInit, OnDestroy {

  jpr: JobPaginationResult = {
    results: [],
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

  orderOptions: string[] = [];

  categories: string[] = [];

  private jobsSub: Subscription;
  private transSub: Subscription;
  private searchOptionsSub: Subscription;

  constructor(
    private discoverService: DiscoverService,
    private router: Router,
    private route: ActivatedRoute,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });

    this.route.queryParams.subscribe((e) => {
      this.parseQueryParams();
      this.discoverService.getJobs(this.jpq);
    })

    this.jobsSub = this.discoverService.results.subscribe(
      (results) => {
        this.jpr = {
          ...this.jpr,
          ...results
        };
        this.isFetching = false;
      });

    this.discoverService.getSearchOptions();

    this.searchOptionsSub = this.discoverService.searchOptions.subscribe((searchOptions) => {
      if (!!searchOptions) {
        this.categories = searchOptions.find((option) => {
          return option.key === "categories"
        }).values;
        this.orderOptions = searchOptions.find((option) => {
          return option.key === "order"
        }).values
      }
    });

    this.discoverService.getStates().subscribe((states) => {
      this.states = states;
    }, () => {
      this.router.navigate(["/500"]);
    });
  }

  changeTitle() {
    this.translateService.get("discover.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  onChangeCategory(category: string) {
    this.jpq.category = category;
    this.jpq.page = 0;
    if (!category) {
      delete this.jpq.category;
    }
    this.updateRoute(false);
  }

  onChangeOrder(order: string) {
    this.jpq.order = order;
    this.jpq.page = 0;
    this.updateRoute(false);
  }

  onChangeState(state: string, id: string) {
    this.selectedState = state;
    this.jpq.state = id;
    this.jpq.page = 0;
    if (!!state) {
      this.discoverService.getStateCities(id).subscribe((cities) => {
        this.cities = cities;
      });
    } else {
      delete this.jpq.state;
    }
    delete this.jpq.city;
    this.selectedCity = "";
    this.cities = [];
    this.updateRoute(false);
  }

  onChangeCity(city: string, id: string) {
    this.selectedCity = city;
    this.jpq.city = id;
    this.jpq.page = 0;
    if (!id) {
      delete this.jpq.city;
    }
    this.updateRoute(false);
  }

  onChangePage(page: number) {
    this.jpq.page = page;
    this.updateRoute(false);
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
      this.updateRoute(false);
    }
  }

  checkLength(query: string) {
    this.searchError = query.length > 50;
  }

  private updateRoute(replace: boolean) {
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: {...this.jpq},
        replaceUrl: replace
      });
  }

  private parseQueryParams() {
    const params = this.route.snapshot.queryParams;

    this.jpq = {
      ...this.jpq,
      ...params
    }

    if (params["page"]) {
      this.jpq.page = Number.parseInt(params["page"])
      // this.jpq.page = isNaN(this.jpq.page) ? 0 : this.jpq.page;
    } else {
      this.jpq.page = 0;
    }

    if (params["pageSize"]) {
      this.jpq.pageSize = Number.parseInt(params["pageSize"])
      this.jpq.pageSize = isNaN(this.jpq.pageSize) ? 6 : this.jpq.pageSize;
    } else {
      this.jpq.pageSize = 6
    }

    if (!params["category"]) {
      delete this.jpq.category;
    }

    if (!params["state"]) {
      delete this.jpq.state;
    }

    if (!params["city"]) {
      delete this.jpq.city;
    }

  }

  ngOnDestroy(): void {
    this.jobsSub.unsubscribe();
    this.transSub.unsubscribe();
    this.searchOptionsSub.unsubscribe();
  }

}



