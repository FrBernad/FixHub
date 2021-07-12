import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../auth/user.service";
import {Subscription} from "rxjs";
import {User} from "../../models/user.model";
import {Form, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-session-profile',
  templateUrl: './session-profile.component.html',
  styleUrls: ['./session-profile.component.scss']
})
export class SessionProfileComponent implements OnInit, OnDestroy {

  private userSub: Subscription;
  profileImage: FormControl;
  coverImage: FormControl;

  allowedCoverImageTypes: string[] = ['image/png', 'image/jpeg','image/jpg'];
  maxCoverImageMBSize = 3;
  allowedCoverImageType: boolean = true;
  allowedCoverImageSize: boolean = true;

  allowedProfileImageTypes: string[] = ['image/png', 'image/jpeg','image/gif','image/jpg'];
  maxProfileImageMBSize = 3;
  allowedProfileImageType: boolean = true;
  allowedProfileImageSize: boolean = true;

  user: User;

  constructor( private userService: UserService) { }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.user = user;
    });

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

    if(!this.allowedProfileImageTypes.includes(file.type)) {
      this.allowedProfileImageType = false;
      return;
    }

    if(file.size > this.getTotalBytes(this.maxProfileImageMBSize)) {
      this.allowedProfileImageSize = false;
      return
    }

    console.log(file);
    console.log(this.profileImage);


  }

  private getTotalBytes(mb){
    return mb * Math.pow(10,6);
  }

  onCoverImageChanged(event) {
    const file = event.target.files[0];
    this.allowedCoverImageType = true;
    this.allowedCoverImageSize = true;

    if(!this.allowedCoverImageTypes.includes(file.type)) {
      this.allowedCoverImageType = false;
      return;
    }

    if(file.size > this.getTotalBytes(this.maxCoverImageMBSize)) {
      this.allowedCoverImageSize = false;
      return
    }

    console.log(this.coverImage);


  }

}
