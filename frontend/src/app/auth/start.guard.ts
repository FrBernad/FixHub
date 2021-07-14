import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthService} from "./auth.service";

@Injectable({providedIn: 'root'})
export class StartGuard implements CanActivate {

  constructor(
    private authService: AuthService,
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              router: RouterStateSnapshot,
  ): |
    boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    return this.authService.autoLogin;
  }

}
