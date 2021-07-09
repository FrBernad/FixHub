import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {User} from '../models/user.model';

@Component({
  selector: 'app-update-info',
  templateUrl: './update-info.component.html',
  styleUrls: ['./update-info.component.scss']
})
export class UpdateInfoComponent implements OnInit {

  loggedUser: User = new User(1, "","","","","","","","",[]);

  userInfoForm: FormGroup;

  maxNameLength: number = 50;
  maxSurnameLength: number = 50;
  maxPhoneNumberLength: number = 50;
  maxStateLength: number = 50;
  maxCityLength: number = 50;

  constructor() {
  }

  ngOnInit(): void {
    this.userInfoForm = new FormGroup({
      name: new FormControl(this.loggedUser.name, [Validators.required, Validators.maxLength(this.maxNameLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$")]),
      surname: new FormControl(this.loggedUser.surname, [Validators.required, Validators.maxLength(this.maxSurnameLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]*$")]),
      phoneNumber: new FormControl(this.loggedUser.phoneNumber, [Validators.required, Validators.maxLength(this.maxPhoneNumberLength), Validators.pattern("^[+]?(?:(?:00)?549?)?0?(?:11|15|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$")]),
      state: new FormControl(this.loggedUser.state, [Validators.required, Validators.maxLength(this.maxStateLength), Validators.pattern("^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      city: new FormControl(this.loggedUser.city, [Validators.required, Validators.maxLength(this.maxCityLength), Validators.pattern("^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
    })
  }

  onSubmit() {
    if (!this.userInfoForm.valid) {
      this.userInfoForm.markAllAsTouched();
      return;
    }
  }


}
