import {Injectable} from '@angular/core';
import {Router, RoutesRecognized} from "@angular/router";
import {filter, pairwise} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class PreviousRouteService {

  private previousUrl = null;
  private authRedirect = false;

  constructor(
    private router: Router
  ) {
    this.router.events.pipe(
      filter(e => e instanceof RoutesRecognized),
      pairwise())
      .subscribe((event: any[]) => {
        this.previousUrl = event[0].urlAfterRedirects;
      });
  }

  getPreviousUrl() {
    return this.previousUrl;
  }

  setAuthRedirect(value: boolean) {
    this.authRedirect = value;
  }

  getAuthRedirect(){
    return this.authRedirect;
  }

}
