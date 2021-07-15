import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  showPass1 = true;
  showPass2 = true;
  minPasswordLength: number = 6;
  maxPasswordLength: number = 20;
  disable = false;
  resetPasswordForm: FormGroup;

  success = false;
  token: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
  ) {
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParams['token'];

    if (!this.token) {
      this.router.navigate(['/']);
    }

    this.resetPasswordForm = new FormGroup({
      password: new FormControl("", [
        Validators.required,
        Validators.minLength(this.minPasswordLength),
        Validators.maxLength(this.maxPasswordLength)]),
      confirmPassword: new FormControl("", [
        Validators.required,
        Validators.minLength(this.minPasswordLength),
        Validators.maxLength(this.maxPasswordLength)
      ])
    });
    this.resetPasswordForm.setValidators(this.passwordMatching.bind(this));
  }

  passwordMatching(group: FormGroup): { [s: string]: boolean } {
    const confirmPasswordControl = group.controls['confirmPassword'];
    if (group.controls['password'].value != confirmPasswordControl.value) {
      confirmPasswordControl.setErrors({passwordsDontMatch: true});
    } else {
      confirmPasswordControl.setErrors(null);
    }
    return;
  }

  toogleEye1() {
    this.showPass1 = !this.showPass1;
  }

  toogleEye2() {
    this.showPass2 = !this.showPass2;
  }

  onSubmit() {
    this.disable = true;
    if (!this.resetPasswordForm.valid) {
      this.resetPasswordForm.markAllAsTouched();
      this.disable = false;
      return;
    }

    let modal = new bootstrap.Modal(document.getElementById('resultsModal'));
    this.authService.resetPassword(this.token, this.resetPasswordForm.get("password").value).subscribe(() => {
      modal.show();
    }, () => {
      modal.show();
      this.disable = false;
    })
  }

  onClose() {
    this.router.navigate(['/login']);
    this.resetPasswordForm.reset();
  }

}
