import {Component, OnDestroy, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {
  faPlus as fasPlus,
  faStar as fasStar,
  faSearch as fasSearch,
  faHandshake as fasHandshake,
  faChevronDown as fasChevronDown,
  faCamera as fasCamera,
  faPhoneAlt as fasPhoneAlt,
  faUserFriends as fasUserFriends,
  faTimes as fasTimes,
  faCheck as fasCheck,
  faMapMarkerAlt as fasMapMarkerAlt,
  faChartLine as fasChartLine,
  faWrench as fasWrench,
  faAddressBook as fasAddressBook,
  faPen as fasPen,
  faCompass as fasCompass,
  faClock as fasClock,
  faTrash as fasTrash,
  faUpload as fasUpload,
  faInfoCircle as fasInfoCircle,
} from '@fortawesome/free-solid-svg-icons';

import {
  faEyeSlash as farEyeSlash,
  faEye as farEye,
  faStar as farStar,
  faEnvelope as farEnvelope
} from '@fortawesome/free-regular-svg-icons';

import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {AuthService} from "./auth/auth.service";
import {PreviousRouteService} from "./auth/previous-route.service";
import {JobsService} from "./discover/jobs.service";
import {Subscription} from "rxjs";
import {UserService} from "./auth/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})

export class AppComponent implements OnInit, OnDestroy {
  private subs: Subscription;

  loading;

  constructor(public translateService: TranslateService,
              public library: FaIconLibrary,
              private router: Router,
              private authService: AuthService,
              private userService: UserService,
              private previousRouteService: PreviousRouteService,
              private jobsService: JobsService,
              private titleService: Title
  ) {
  }

  ngOnInit(): void {
    this.translateService.addLangs(['en', 'es']);
    this.translateService.setDefaultLang('en');

    this.library.addIcons(fasPlus, fasSearch,
      fasStar, fasHandshake,
      farEyeSlash, farEye,
      fasChevronDown, fasCamera,
      farEnvelope, fasPhoneAlt,
      fasUserFriends, fasTimes,
      fasCheck, fasMapMarkerAlt,
      fasChartLine, fasWrench, fasAddressBook, farStar,
      fasPen, fasCompass, fasClock, fasTrash, fasUpload,
      fasInfoCircle
    );

    this.authService.autoLogin();

    this.subs = this.userService.loading.subscribe((loading) => {
      this.loading = loading;
    })

  }

  translateSite(language: string) {
    this.translateService.use(language);
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }


}
