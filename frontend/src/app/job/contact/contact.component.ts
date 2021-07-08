import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { faAirFreshener } from '@fortawesome/free-solid-svg-icons';
import { Job } from '../../models/Job';
import { User } from '../../models/User';
import { Contact } from './contact.model';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent implements OnInit {
  contactForm: FormGroup;
  contactInfoCollection: Contact[] = [
    new Contact('State', 'City', 'Street', 120, 1, 'C', 'I need the job'),
  ];
  provider: User = {
    id: 2,
    name: 'Agus',
    surname: 'Manfredi',
    email: 'agus@yopmail.com',
    phoneNumber: '+5491112345678',
    state: 'Buenos Aires',
    city: 'Adrogue',
    profileImage: 1,
    coverImage: 2,
    following: [],
    followers: [],
    providerDetails: {
      location: {
        cities: [{ id: 1, name: 'Burzaco' }],
        state: { id: 1, name: 'Buenos Aires' },
      },
      schedule: {
        startTime: new Date(),
        endTime: new Date(),
      },
    },
  };
  job: Job = {
    id: 1,
    description: 'asda',
    jobProvided: 'dasda',
    category: 'CARPINTERO',
    price: 121,
    totalRatings: 0,
    averageRating: 0,
    thumbnailId:1,
    images: [],
    thumbnailId: 1,
    reviews: [],
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


  constructor() {}

  ngOnInit(): void {
    this.contactForm = new FormGroup({
    state: new FormControl(this.job.provider.providerDetails.location.state.name, [Validators.required, Validators.maxLength(this.maxStateLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      city: new FormControl(null, [Validators.required, Validators.maxLength(this.maxCityLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      street: new FormControl(null, [Validators.required, Validators.maxLength(this.maxStreetLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      addressNumber: new FormControl(null, [Validators.required, Validators.pattern("[0-9]{0,9}")]),
      floor: new FormControl(null, Validators.pattern("[0-9]{0,9}")),
      departmentNumber: new FormControl(null, [Validators.pattern("[A-Za-z0-9]{0,30}"), Validators.maxLength(this.maxDepartmentLength)]),
      message: new FormControl(null, [Validators.required, Validators.maxLength(this.maxMessageLength)]),
      contactInfoId: new FormControl(-1, Validators.required)
    });
  }

  onSubmit() {
    console.log(this.contactForm);
  }
}
