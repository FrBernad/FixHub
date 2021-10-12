import {Component, OnDestroy, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {UserService} from "../../auth/services/user.service";
import {Subscription} from "rxjs";
import {User} from "../../models/user.model";
import {RequestsService} from "./requests.service";

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit, OnDestroy {

  private userSub: Subscription;
  private transSub: Subscription;
  private contactServiceSub: Subscription;

  orderOptions: string[] = [];
  status: string[] = [];

  user: User;

  constructor(
    private userService: UserService,
    private titleService: Title,
    private translateService: TranslateService,
    private contactService: RequestsService,
  ) {
  }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    });

    this.changeTitle();

    this.contactService.getSearchOptions();

    this.contactServiceSub = this.contactService.searchOptions.subscribe((searchOptions) => {
      if (!!searchOptions) {
        this.status = searchOptions.find((option) => {
          return option.key === "status"
        }).values;
        this.orderOptions = searchOptions.find((option) => {
          return option.key === "order"
        }).values
      }
    });

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });
  }

  changeTitle() {
    this.translateService.get("requests.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
    this.transSub.unsubscribe();
    this.contactServiceSub.unsubscribe();
  }

}
