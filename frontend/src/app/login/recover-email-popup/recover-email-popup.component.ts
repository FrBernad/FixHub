import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-recover-email-popup',
  templateUrl: './recover-email-popup.component.html',
  styleUrls: ['./recover-email-popup.component.scss']
})
export class RecoverEmailPopupComponent implements OnInit {
  constructor(
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
  }

  onSubmit(){
    this.authService.sendResetPasswordEmail("pepe@yopmail.com").subscribe(()=>{
      console.log("recovery sent")
    });
  }

}
