import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

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

  resetPasswordForm: FormGroup;

  constructor() {
  }

  ngOnInit(): void {
    this.resetPasswordForm = new FormGroup({
      password: new FormControl("",[
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
    if(group.controls['password'].value != confirmPasswordControl.value){
      confirmPasswordControl.setErrors({ passwordsDontMatch: true });
    }else {
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

  onSubmit(){

    if (!this.resetPasswordForm.valid) {
      this.resetPasswordForm.markAllAsTouched();
      return;
    }
  }
}
