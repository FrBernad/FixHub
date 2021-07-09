import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../models/user.model';
import {Contact} from './contact.model';
import {JobCategoryModel} from "../../models/jobCategory.model";
import {Job} from "../../models/job.model";

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
  provider: User = new User(1, "","","","","","","","",[]);
  job: Job = {
    id: 1,
    description: 'Fotos de 15 o Casamientos',
    jobProvided: 'Fotos eventos',
    category: JobCategoryModel.FOTOGRAFO,
    price: 121,
    totalRatings: 0,
    averageRating: 0,
    thumbnailId: 1,
    images: [],
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

  city = new FormControl(null, [Validators.required, Validators.maxLength(this.maxCityLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]);


  constructor() {
  }

  ngOnInit(): void {
    this.contactForm = new FormGroup({
      state: new FormControl(this.job.provider.providerDetails.location.state.name, [Validators.required, Validators.maxLength(this.maxStateLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      city: this.city,
      street: new FormControl(null, [Validators.required, Validators.maxLength(this.maxStreetLength), Validators.pattern("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      addressNumber: new FormControl(null, [Validators.required, Validators.pattern("[0-9]{0,9}")]),
      floor: new FormControl(null, Validators.pattern("[0-9]{0,9}")),
      departmentNumber: new FormControl(null, [Validators.pattern("[A-Za-z0-9]{0,30}"), Validators.maxLength(this.maxDepartmentLength)]),
      message: new FormControl(null, [Validators.required, Validators.maxLength(this.maxMessageLength)]),
      contactInfoId: new FormControl(-1, Validators.required)
    });
  }

  onSubmit() {
    if (!this.contactForm.valid) {
      this.contactForm.markAllAsTouched();
      return;
    }
    console.log(this.contactForm);
  }

  dropdownClick(city) {
    this.city.setValue(city.name);
  }
}
