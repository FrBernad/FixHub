import {Component, OnDestroy, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-errors',
  templateUrl: './errors.component.html',
  styleUrls: ['./errors.component.scss']
})
export class ErrorsComponent implements OnInit, OnDestroy {
  private transSub: Subscription;

  code: number = 404;

  constructor(
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {
    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });
  }

  changeTitle() {
    this.translateService.get("pageNotFound.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  ngOnDestroy(): void {
    this.transSub.unsubscribe();
  }

}
