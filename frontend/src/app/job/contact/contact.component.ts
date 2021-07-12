import { ContactInfo } from './../../models/contactInfo.model';
import { ContactService } from './contact.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../../models/user.model';
import { Contact } from './contact.model';
import { JobCategoryModel } from '../../models/jobCategory.model';
import { Job } from '../../models/job.model';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/auth/user.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent implements OnInit {
  contactForm: FormGroup;
   contactInfoCollection: ContactInfo[] = [];

  provider: User = new User(1, '', '', '', '', '', '', '', '', [], 1,2,{
    location: {
      cities: [{ id: 1, name: 'Olivos' }],
      state: { id: 1, name: 'Buenos Aires' },
    },
    schedule: {
      startTime: new Date(2018, 11, 24, 10, 33, 30, 0),
      endTime: new Date(2018, 11, 24, 15, 33, 30, 0),
    },
    jobsCount:20,
    avgRating:3,
    reviewCount:45

  });

  job: Job = {
    id: 1,
    description: 'Fotos de 15 o Casamientos',
    jobProvided: 'Fotos eventos',
    category: JobCategoryModel.FOTOGRAFO,
    price: 121,
    totalRatings: 0,
    averageRating: 0,
    thumbnailImage: '',
    images: [],
    provider: this.provider,
    paused: false,
  };

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
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe((user) => {
      this.user = user;
    });

    this.contactService
      .getContactInfo(this.user.id)
      .subscribe((responseData) => {
        responseData.forEach((contactInfo) => {
          this.contactInfoCollection.push(contactInfo);
        });
      });

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
      floor: new FormControl(null, Validators.pattern('[0-9]{0,9}')),
      departmentNumber: new FormControl(null, [
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

  onSubmit() {
    if (!this.contactForm.valid) {
      this.contactForm.markAllAsTouched();
      return;
    }
    console.log(this.contactForm);
  }

  dropdownClickCity(city: { id: number; name: string }) {
    this.city.setValue(city.name);
  }

  dropdownClickContact(contact: ContactInfo) {
    this.contactForm.get('contactInfoId').setValue(contact.id);
    this.contactForm.get('addressNumber').setValue(null);
    this.city.setValue(contact.city);
    this.contactForm.get('departmentNumber').setValue(contact.departmentNumber);
    this.contactForm.get('floor').setValue(contact.floor);
    this.contactForm.get('street').setValue(contact.street);
  }

  dropdownClickNew() {
    this.contactForm.get('contactInfoId').setValue(-1);
    this.contactForm.get('addressNumber').setValue(null);
    this.city.setValue(null);
    this.contactForm.get('departmentNumber').setValue(null);
    this.contactForm.get('floor').setValue(null);
    this.contactForm.get('street').setValue(null);
  }

  cityNotIncluded() {
    const formCity = this.contactForm.get('city').value;
    return (
      formCity != null &&
      formCity != '' &&
      !this.provider.providerDetails.location.cities.some(
        (city) => city.name === formCity
      )
    );
  }
}
