import {Component, OnInit} from '@angular/core';
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
import {ActivatedRoute, Data, NavigationEnd, Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {filter, map, take, tap} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})


export class AppComponent implements OnInit {
  title = 'frontend';

  static appName = "Fixhub";


  constructor(public translateService: TranslateService,
              public library: FaIconLibrary,
              private router: Router,
              private titleService: Title) {

  }

  ngOnInit(): void {
    this.translateService.addLangs(['en', 'es']);
    this.translateService.setDefaultLang('en');

    this.library.addIcons(fasPlus, fasSearch, fasStar, fasHandshake, farEyeSlash, farEye);
    // this.router.events
    //   .pipe(
    //     filter((event) => event instanceof NavigationEnd),
    //     map(() => this.router.routerState.root)
    //   )
    //   .subscribe((event) => {
    //     console.log('NavigationEnd:', event);
    //   });

    // this.route.data.subscribe((data: Data) => {
    //   this.translateService.get(data.title).pipe(take(1), tap((res: string) => {
    //     if (!res) {
    //       this.titleService.setTitle(AppComponent.appName);
    //     } else {
    //       this.titleService.setTitle(AppComponent.appName + " | " + res);
    //     }
    //   }))
    // })
  }

  translateSite(language
                  :
                  string
  ) {
    this.translateService.use(language);
  }


}
