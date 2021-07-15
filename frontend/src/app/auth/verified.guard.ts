import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map, take} from 'rxjs/operators';

import {PreviousRouteService} from "./previous-route.service";
import {UserService} from "./user.service";

@Injectable({providedIn: 'root'})
export class VerifiedGuard implements CanActivate {

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
        if (!!user && user.roles.includes("VERIFIED")) {
          return true;
        }
        return this.router.createUrlTree(["/user/profile"]);
      })
    );
  }

}
