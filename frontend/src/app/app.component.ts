import {Component, OnDestroy, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {
  faAddressBook as fasAddressBook,
  faCamera as fasCamera,
  faChartLine as fasChartLine,
  faCheck as fasCheck,
  faChevronDown as fasChevronDown,
  faClock as fasClock,
  faComment as fasComment,
  faCompass as fasCompass,
  faExclamationCircle as fasExclamationCircle,
  faHandshake as fasHandshake,
  faInfoCircle as fasInfoCircle,
  faMapMarkerAlt as fasMapMarkerAlt,
  faPen as fasPen,
  faPhoneAlt as fasPhoneAlt,
  faPlus as fasPlus,
  faSearch as fasSearch,
  faStar as fasStar,
  faTimes as fasTimes,
  faTrash as fasTrash,
  faUpload as fasUpload,
  faUserFriends as fasUserFriends,
  faWrench as fasWrench
} from '@fortawesome/free-solid-svg-icons';

import {
  faCheckCircle as farCheckCircle,
  faEnvelope as farEnvelope,
  faEye as farEye,
  faEyeSlash as farEyeSlash,
  faStar as farStar
} from '@fortawesome/free-regular-svg-icons';

import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {AuthService} from "./auth/services/auth.service";
import {PreviousRouteService} from "./auth/services/previous-route.service";
import {DiscoverService} from "./discover/discover.service";
import {Subscription} from "rxjs";
import {UserService} from "./auth/services/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})

export class AppComponent implements OnInit, OnDestroy {
  private subs: Subscription;

  loading: boolean;

  constructor(
    public translateService: TranslateService,
    public library: FaIconLibrary,
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private previousRouteService: PreviousRouteService,
    private jobsService: DiscoverService,
    private titleService: Title
  ) {
  }

  ngOnInit(): void {
    this.translateService.addLangs(['en', 'es']);
    this.translateService.setDefaultLang('en');
    this.translateService.use(this.translateService.getBrowserLang());

    this.library.addIcons(fasPlus, fasSearch,
      fasStar, fasHandshake,
      farEyeSlash, farEye,
      fasChevronDown, fasCamera,
      farEnvelope, fasPhoneAlt,
      fasUserFriends, fasTimes,
      fasCheck, fasMapMarkerAlt,
      fasChartLine, fasWrench, fasAddressBook, farStar,
      fasPen, fasCompass, fasClock, fasTrash, fasUpload,
      fasInfoCircle, fasExclamationCircle, farCheckCircle,
      fasComment
    );

    this.subs = this.userService.loading.subscribe((loading) => {
      this.loading = loading;
    })

  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }

}
