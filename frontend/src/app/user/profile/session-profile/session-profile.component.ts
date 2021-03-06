import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FormControl} from "@angular/forms";
import {User} from "../../../models/user.model";
import {UserService} from "../../../auth/services/user.service";
import {AuthService} from "../../../auth/services/auth.service";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-session-profile',
  templateUrl: './session-profile.component.html',
  styleUrls: ['./session-profile.component.scss']
})
export class SessionProfileComponent implements OnInit, OnDestroy {

  private transSub: Subscription;
  private userSub: Subscription;
  user: User;

  profileImage: FormControl;
  coverImage: FormControl;

  coverChange = "";
  profileChange = "";

  allowedCoverImageTypes: string[] = ['image/png', 'image/jpeg', 'image/jpg'];
  maxCoverImageMBSize = 3;
  allowedCoverImageType: boolean = true;
  allowedCoverImageSize: boolean = true;

  allowedProfileImageTypes: string[] = ['image/png', 'image/jpeg', 'image/gif', 'image/jpg'];
  maxProfileImageMBSize = 3;
  allowedProfileImageType: boolean = true;
  allowedProfileImageSize: boolean = true;

  disable = false;
  disabledCover = false;
  disabledProfile = false;
  success = false;


  constructor(
    private userService: UserService,
    private authService: AuthService,
    private titleService: Title,
    private translateService: TranslateService,
  ) {
  }

  ngOnInit(): void {

    this.userService.repopulateUserData().subscribe();

    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    });

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });

    if (!this.user.roles.includes("VERIFIED")) {
      let modal = new bootstrap.Modal(document.getElementById('notVerifiedModal'));
      modal.show();
    }

    this.profileImage = new FormControl(null);
    this.coverImage = new FormControl(null);

  }

  changeTitle() {
    this.translateService.get("profilePage.subtitle.account")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }


  onProfileFileChanged(event) {
    const file = event.target.files[0];
    this.allowedProfileImageType = true;
    this.allowedProfileImageSize = true;

    if (!file) {
      return;
    }

    if (!this.allowedProfileImageTypes.includes(file.type)) {
      this.allowedProfileImageType = false;
      setTimeout(() => {
        this.allowedProfileImageType = true;
      }, 2000);
      return;
    }

    if (file.size > this.getTotalBytes(this.maxProfileImageMBSize)) {
      this.allowedProfileImageSize = false;
      setTimeout(() => {
        this.allowedProfileImageSize = true;
      }, 2000);
      return
    }

    this.disabledProfile = true;

    let profileImageUpload = new FormData();
    profileImageUpload.append('profileImage', file);

    this.userService.updateProfileImage(profileImageUpload).subscribe(
      () => {
        this.profileChange = "?" + new Date().getMilliseconds();
        this.disabledProfile = false;
      });

  }

  private getTotalBytes(mb) {
    return mb * Math.pow(10, 6);
  }

  onCoverImageChanged(event) {
    const file = event.target.files[0];
    this.allowedCoverImageType = true;
    this.allowedCoverImageSize = true;

    if (!file) {
      return;
    }

    if (!this.allowedCoverImageTypes.includes(file.type)) {
      this.allowedCoverImageType = false;
      setTimeout(() => {
        this.allowedCoverImageType = true;
      }, 2000);
      return;
    }

    if (file.size > this.getTotalBytes(this.maxCoverImageMBSize)) {
      this.allowedCoverImageSize = false;
      setTimeout(() => {
        this.allowedCoverImageSize = true;
      }, 2000);
      return
    }

    this.disabledCover = true;

    let coverImageUpload = new FormData();
    coverImageUpload.append('coverImage', file);

    this.userService.updateCoverImage(coverImageUpload).subscribe(
      () => {
        this.coverChange = "?" + new Date().getMilliseconds();
        this.disabledCover = false;
      }
    );
  }

  onVerify() {
    this.disable = true;
    this.authService.resendVerificationEmail().subscribe(() => {
      this.disable = false;
      this.success = true;
      setTimeout(() => {
        this.success = false;
      }, 2000)
    }, () => {
      this.disable = false;
    })
  }

  onClose() {
    this.disable = false;
    this.success = false;
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
    this.transSub.unsubscribe();
  }
}
