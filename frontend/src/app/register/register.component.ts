import { FormControl, FormGroup, Validators } from '@angular/forms';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  showPass1 = true;
  showPass2 = true;

  registerForm: FormGroup;

  maxNameLength: number = 50;
  maxSurnameLength: number = 50;
  maxEmailLength: number = 200;
  minPasswordLength: number = 6;
  maxPasswordLength: number = 20;
  maxPhoneNumberLength: number = 50;
  maxStateLength: number = 50;
  maxCityLength: number = 50;

  constructor() {
  }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      name: new FormControl(null, [Validators.required, Validators.maxLength(this.maxNameLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$")]),
      surname: new FormControl(null, [Validators.required, Validators.maxLength(this.maxSurnameLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$")]),
      email: new FormControl(null, [Validators.required, Validators.maxLength(this.maxEmailLength), Validators.pattern("^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$")]),
      password: new FormControl(null, [Validators.required, Validators.minLength(this.minPasswordLength), Validators.maxLength(this.maxPasswordLength)]),
      phoneNumber: new FormControl(null, [Validators.required, Validators.maxLength(this.maxPhoneNumberLength), Validators.pattern("^[+]?(?:(?:00)?549?)?0?(?:11|15|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$")]),
      state: new FormControl(null, [Validators.required, Validators.maxLength(this.maxStateLength), Validators.pattern("^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      city: new FormControl(null, [Validators.required, Validators.maxLength(this.maxCityLength), Validators.pattern("^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
    })
  }

  toogleEye1() {
    this.showPass1 = !this.showPass1;
  }

  toogleEye2() {
    this.showPass2 = !this.showPass2;
  }

  onSubmit() {
    if(!this.registerForm.valid){
      this.registerForm.markAllAsTouched();
      return;
    }
    console.log(this.registerForm);
  }


}
