import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {User} from '../../../models/user.model';
import {UserService} from "../../../auth/user.service";

@Component({
  selector: 'app-update-info',
  templateUrl: './update-info.component.html',
  styleUrls: ['./update-info.component.scss']
})
export class UpdateInfoComponent implements OnInit {

  @Input() loggedUser: User;

  modal: any;

  userInfoForm: FormGroup;

  maxNameLength: number = 50;
  maxSurnameLength: number = 50;
  maxPhoneNumberLength: number = 50;
  maxStateLength: number = 50;
  maxCityLength: number = 50;
  disabled = false;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.modal = new bootstrap.Modal(document.getElementById("updateInfo"));
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
    this.disabled = true;
    this.userService.updateProfileInfo(this.userInfoForm.value).subscribe(
      () =>{
        this.disabled = false;
         this.modal.hide();
      });
   
  }

  onClose() {
    this.userInfoForm.get('name').patchValue(this.loggedUser.name);
    this.userInfoForm.get('surname').patchValue(this.loggedUser.surname);
    this.userInfoForm.get('phoneNumber').patchValue(this.loggedUser.phoneNumber);
    this.userInfoForm.get('state').patchValue(this.loggedUser.state);
    this.userInfoForm.get('city').patchValue(this.loggedUser.city);
  }

}
