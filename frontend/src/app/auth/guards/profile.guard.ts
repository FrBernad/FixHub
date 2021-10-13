import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map, take} from 'rxjs/operators';

import {PreviousRouteService} from "../services/previous-route.service";
import {UserService} from "../services/user.service";

@Injectable({providedIn: 'root'})
export class ProfileGuard implements CanActivate {

  constructor(
    private userService: UserService,
    private router: Router
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
        if (!user || (!!user && route.params['id'] != user.id)) {
          return true;
        }
        let previousRoute = "/user/profile";
        return this.router.createUrlTree([previousRoute]);
      })
    );
  }

}
