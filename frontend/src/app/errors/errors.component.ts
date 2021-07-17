import {Component, OnDestroy, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-errors',
  templateUrl: './errors.component.html',
  styleUrls: ['./errors.component.scss']
})
export class ErrorsComponent implements OnInit, OnDestroy {
  private transSub: Subscription;

  code: number = 404;
  i18:string = "pageNotFound";

  constructor(
    private titleService: Title,
    private translateService: TranslateService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.i18 = this.route.snapshot.data["i18"];
    this.code = this.route.snapshot.data["code"];

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });
  }

  changeTitle() {
    this.translateService.get(this.i18+".title")
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
