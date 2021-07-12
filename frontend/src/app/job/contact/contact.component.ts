import {JobService} from './../job.service';
import {ActivatedRoute, Params} from '@angular/router';
import {ContactInfo} from './../../models/contactInfo.model';
import {ContactService} from './contact.service';
import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../models/user.model';
import {Job} from '../../models/job.model';
import {Subscription} from 'rxjs';
import {UserService} from 'src/app/auth/user.service';


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent implements OnInit {
  contactForm: FormGroup;
  contactInfoCollection: ContactInfo[] = [];

  @Input()
  job: Job;

  jobId: number;
  isFetching: boolean = true;

  maxStateLength: number = 50;
  maxCityLength: number = 50;
  maxStreetLength: number = 50;
  maxMessageLength: number = 300;
  maxFloorLength: number = 9;
  maxAddressNumber: number = 9;
  maxDepartmentLength: number = 30;

  private userSub: Subscription;
  user: User;

  city = new FormControl(null, [
    Validators.required,
    Validators.maxLength(this.maxCityLength),
    Validators.pattern(
      "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
    ),
  ]);

  constructor(
    private contactService: ContactService,
    private userService: UserService,
  ) {}

  ngOnInit(): void {

    this.userSub = this.userService.user.subscribe((user) => {
      this.user = user;
    });

    this.jobId = this.job.id;

    this.initForm();

    this.contactService
      .getContactInfo(this.user.id)
      .subscribe((responseData) => {
        responseData.forEach((contactInfo) => {
          this.contactInfoCollection.push(contactInfo);
        });
      });
  }

  onSubmit() {
    if (!this.contactForm.valid) {
      this.contactForm.markAllAsTouched();
      return;
    }

    this.contactService.newContact(this.jobId, {
      state: this.job.provider.providerDetails.location.state.name,
      city: this.contactForm.get('city').value,
      street: this.contactForm.get('street').value,
      floor: this.contactForm.get('floor').value,
      departmentNumber: this.contactForm.get('departmentNumber').value,
      message: this.contactForm.get('message').value,
      contactInfoId: this.contactForm.get('contactInfoId').value,
      userId: '' + this.user.id,
      addressNumber: this.contactForm.get('addressNumber').value,
    }).subscribe(
      response => {
        console.log(response);
      }
    );

    this.onClose();
  }

  dropdownClickCity(city: { id: number; name: string }) {
    this.city.setValue(city.name);
  }

  dropdownClickContact(contact: ContactInfo) {
    this.contactForm.get('contactInfoId').setValue(contact.id);
    this.contactForm.get('addressNumber').setValue(contact.addressNumber);
    this.city.setValue(contact.city);
    this.contactForm.get('departmentNumber').setValue(contact.departmentNumber);
    this.contactForm.get('floor').setValue(contact.floor);
    this.contactForm.get('street').setValue(contact.street);
  }

  resetInfo() {
    this.contactForm.get('contactInfoId').setValue(-1);
    this.contactForm.get('addressNumber').setValue(null);
    this.city.setValue(null);
    this.contactForm.get('departmentNumber').setValue(null);
    this.contactForm.get('floor').setValue(null);
    this.contactForm.get('street').setValue(null);
  }

  onClose() {
    this.resetInfo();
    this.contactForm.get('message').setValue(null);
    this.contactForm.markAsUntouched();
  }

  cityNotIncluded() {
    const formCity = this.contactForm.get('city').value;
    return (
      formCity != null &&
      formCity != '' &&
      !this.job.provider.providerDetails.location.cities.some(
        (city) => city.name === formCity
      )
    );
  }

  private initForm() {
    this.contactForm = new FormGroup({
      state: new FormControl(
        this.job.provider.providerDetails.location.state.name,
        [
          Validators.required,
          Validators.maxLength(this.maxStateLength),
          Validators.pattern(
            "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
          ),
        ]
      ),
      city: this.city,
      street: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxStreetLength),
        Validators.pattern(
          "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
        ),
      ]),
      addressNumber: new FormControl(null, [
        Validators.required,
        Validators.pattern('[0-9]{0,9}'),
      ]),
      floor: new FormControl('', Validators.pattern('[0-9]{0,9}')),
      departmentNumber: new FormControl('', [
        Validators.pattern('[A-Za-z0-9]{0,30}'),
        Validators.maxLength(this.maxDepartmentLength),
      ]),
      message: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxMessageLength),
      ]),
      contactInfoId: new FormControl(-1, Validators.required),
    });
  }
}
