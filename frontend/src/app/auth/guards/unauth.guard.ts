import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map, take} from 'rxjs/operators';

import {AuthService} from '../services/auth.service';
import {PreviousRouteService} from "../services/previous-route.service";

@Injectable({providedIn: 'root'})
export class UnauthGuard implements CanActivate {


  constructor(
    private authService: AuthService,
    private router: Router,
    private previousRouteService: PreviousRouteService,
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    return this.authService.session.pipe(
      take(1),
      map(session => {
        if (!session) {
          return true;
        }
        let previousRoute = this.previousRouteService.getPreviousUrl();
        previousRoute = !!previousRoute ? previousRoute : "/user/profile"
        return this.router.createUrlTree([previousRoute]);
      })
    );
  }

}
