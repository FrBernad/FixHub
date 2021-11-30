import {Component, ElementRef, HostListener, OnDestroy, OnInit, Renderer2, ViewChild,} from '@angular/core';
import {AuthService} from "../../auth/services/auth.service";
import {Subscription} from "rxjs";
import {User} from "../../models/user.model";
import {UserService} from "../../auth/services/user.service";
import {NotificationsService} from "../../user/notifications/notifications.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit, OnDestroy {

  @ViewChild('backNavbar') backNavbar: ElementRef;
  @ViewChild('navbar') navbar: ElementRef;

  private userSub: Subscription;
  private notSub: Subscription;
  user: User;
  newNotifications: boolean;

  constructor(
    private userService: UserService,
    private notificationsService: NotificationsService,
    private authService: AuthService,
    private renderer: Renderer2
  ) {
  }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    });


    this.newNotifications = this.notificationsService.newNotifications.getValue();

    this.notSub = this.notificationsService.newNotifications.subscribe(
      (res) => {
        this.newNotifications = res;
      }
    )
  }

  @HostListener('window:scroll') onScrollHost(): void {
    let scrolled = document.scrollingElement.scrollTop;
    if (scrolled > 0) {
      this.renderer.removeClass(this.backNavbar.nativeElement, "d-none");
      this.renderer.addClass(this.backNavbar.nativeElement, "d-block");
      this.renderer.removeClass(this.navbar.nativeElement, "navbarTop");
      this.renderer.addClass(this.navbar.nativeElement, "navbarScrolled");
    } else {
      this.renderer.removeClass(this.backNavbar.nativeElement, "d-block");
      this.renderer.addClass(this.backNavbar.nativeElement, "d-none");
      this.renderer.removeClass(this.navbar.nativeElement, "navbarScrolled");
      this.renderer.addClass(this.navbar.nativeElement, "navbarTop");
    }
  }

  onLogout() {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
    this.notSub.unsubscribe();
  }

}
