import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {
  faPlus as fasPlus,
  faStar as fasStar,
  faSearch as fasSearch,
  faHandshake as fasHandshake,
} from '@fortawesome/free-solid-svg-icons';

import {
  faEyeSlash as farEyeSlash,
  faEye as farEye
} from '@fortawesome/free-regular-svg-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';

  constructor(public translateService: TranslateService, public library: FaIconLibrary) {
    translateService.addLangs(['en', 'es']);
    translateService.setDefaultLang('en');

    library.addIcons(fasPlus, fasSearch, fasStar, fasHandshake, farEyeSlash, farEye);
  }

  translateSite(language: string) {
    this.translateService.use(language);
  }
}
