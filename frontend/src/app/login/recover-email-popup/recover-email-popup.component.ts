import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-recover-email-popup',
  templateUrl: './recover-email-popup.component.html',
  styleUrls: ['./recover-email-popup.component.scss']
})
export class RecoverEmailPopupComponent implements OnInit {

  disable = false;
  success = false;
  error = false;

  resetPasswordForm: FormGroup;

  constructor(
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.resetPasswordForm = new FormGroup({
      email: new FormControl("", [
        Validators.required,
        Validators.email]),
    });
  }

  onSubmit() {
    this.disable = true;

    if (!this.resetPasswordForm.valid) {
      this.resetPasswordForm.markAllAsTouched();
      this.disable = false;
      return;
    }
    this.authService.sendResetPasswordEmail(this.resetPasswordForm.get("email").value).subscribe(() => {
      this.disable = false;
      this.success = true;
    }, () => {
      this.error = true;
      this.disable = false;
    });
  }

  onClose() {
    this.error = false;
    this.success = false;
    this.resetPasswordForm.reset();
  }

}
