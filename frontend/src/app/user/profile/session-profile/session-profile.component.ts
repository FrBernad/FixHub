import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../../auth/services/user.service";
import {Subscription} from "rxjs";
import {User} from "../../../models/user.model";
import {FormControl} from "@angular/forms";
import {AuthService} from "../../../auth/services/auth.service";
import {SessionProfileService} from "./session-profile.service";

@Component({
  selector: 'app-session-profile',
  templateUrl: './session-profile.component.html',
  styleUrls: ['./session-profile.component.scss']
})
export class SessionProfileComponent implements OnInit, OnDestroy {

  private userSub: Subscription;
  profileImage: FormControl;
  coverImage: FormControl;

  allowedCoverImageTypes: string[] = ['image/png', 'image/jpeg', 'image/jpg'];
  maxCoverImageMBSize = 3;
  allowedCoverImageType: boolean = true;
  allowedCoverImageSize: boolean = true;

  allowedProfileImageTypes: string[] = ['image/png', 'image/jpeg', 'image/gif', 'image/jpg'];
  maxProfileImageMBSize = 3;
  allowedProfileImageType: boolean = true;
  allowedProfileImageSize: boolean = true;

  disable = false;
  success = false;

  user: User;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private sessionProfileService: SessionProfileService
  ) {
  }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    });

    if (!this.user.roles.includes("VERIFIED")) {
      let modal = new bootstrap.Modal(document.getElementById('notVerifiedModal'));
      modal.show();
    }

    this.profileImage = new FormControl(null);
    this.coverImage = new FormControl(null);
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe()
  }


  onProfileFileChanged(event) {

    const file = event.target.files[0];
    this.allowedProfileImageType = true;
    this.allowedProfileImageSize = true;

    if (!this.allowedProfileImageTypes.includes(file.type)) {
      this.allowedProfileImageType = false;
      return;
    }

    if (file.size > this.getTotalBytes(this.maxProfileImageMBSize)) {
      this.allowedProfileImageSize = false;
      return
    }
    let profileImageUpload = new FormData();
    profileImageUpload.append('profileImage', file);

    this.sessionProfileService.updateProfileImage(profileImageUpload).subscribe((response) => {
      console.log(response);
    });


  }

  private getTotalBytes(mb) {
    return mb * Math.pow(10, 6);
  }

  onCoverImageChanged(event) {
    const file = event.target.files[0];
    this.allowedCoverImageType = true;
    this.allowedCoverImageSize = true;

    if (!this.allowedCoverImageTypes.includes(file.type)) {
      this.allowedCoverImageType = false;
      return;
    }

    if (file.size > this.getTotalBytes(this.maxCoverImageMBSize)) {
      this.allowedCoverImageSize = false;
      return
    }

    let coverImageUpload = new FormData();
    coverImageUpload.append('coverImage', file);

    this.sessionProfileService.updateCoverImage(coverImageUpload).subscribe((response) => {
      console.log(response);
    });
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

}
