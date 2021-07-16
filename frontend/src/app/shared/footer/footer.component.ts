import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Languages} from "../../models/languages-enum.model";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  languages = Object.keys(Languages).filter((item) => {
    return isNaN(Number(item));
  });

  constructor(
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {
  }

  changeLang(lang: string) {
    this.translateService.use(lang);
  }

  currentLang() {
    return this.translateService.currentLang;
  }

}
