import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
  UrlTree
} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map, tap, take} from 'rxjs/operators';

import {PreviousRouteService} from "./previous-route.service";
import {UserService} from "./user.service";

@Injectable({providedIn: 'root'})
export class ProviderGuard implements CanActivate {

  constructor(
    private userService: UserService,
    private router: Router,
    private previousRouteService: PreviousRouteService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              router: RouterStateSnapshot,
  ): |
    boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    return this.userService.user.pipe(
      take(1),
      map(user => {
        if (!!user && user.roles.includes("PROVIDER")) {
          return true;
        }
        let previousRoute = this.previousRouteService.getPreviousUrl();
        previousRoute = !!previousRoute ? previousRoute : "/user/profile"
        return this.router.createUrlTree([previousRoute]);
      })
    );
  }

}
