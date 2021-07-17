import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {UserService} from "../services/user.service";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.scss']
})
export class VerifyComponent implements OnInit, OnDestroy {

  private transSub: Subscription;

  loading = true;
  success = false;

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });

    const token = this.route.snapshot.queryParams['token'];
    this.authService.verify(token).subscribe(() => {
      this.userService.populateUserData().subscribe(() => {
        this.success = true;
        this.loading = false;
      });
    }, () => {
      this.loading = false;
    })

  }

  changeTitle() {
    this.translateService.get("account.verify.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  ngOnDestroy(): void {
    this.transSub.unsubscribe();
  }

}
