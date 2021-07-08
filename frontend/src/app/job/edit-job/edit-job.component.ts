import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../models/User";
import {Job} from "../../models/Job";

@Component({
  selector: 'app-edit-job',
  templateUrl: './edit-job.component.html',
  styleUrls: ['./edit-job.component.scss','../new-job/new-job.component.scss','../job.component.scss']
})
export class EditJobComponent implements OnInit {

  maxImagesPerJob: number = 6;
  maxJobProvidedLength: number = 50;
  maxDescriptionLength: number = 300;
  minPrice: number = 1;
  maxPrice: number = 999999;

  editJobForm: FormGroup;

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
        cities: [{id: 1, name: 'Burzaco'}],
        state: {id: 1, name: 'Buenos Aires'}
      },
      schedule: {
        startTime: new Date(),
        endTime: new Date()
      }
    },
  }

  job: Job = {
    id: 1, description: 'asda', jobProvided: 'dasda',
    category: 'CARPINTERO', price: 121, totalRatings: 0,
    averageRating: 0, images: [], reviews: [],
    provider: this.provider,
    paused: false,
    thumbnailId: 1
  };

  constructor() { }

  ngOnInit(): void {
    this.editJobForm = new FormGroup({
      'jobProvided': new FormControl(null,[Validators.required, Validators.maxLength(this.maxJobProvidedLength), Validators.pattern("^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      'jobCategory': new FormControl(null, [Validators.required]),
      'price': new FormControl(null, [Validators.required, Validators.min(this.minPrice), Validators.max(this.maxPrice)]),
      'description': new FormControl(null, [Validators.required, Validators.maxLength(this.maxDescriptionLength)])
    })
  }

  onSubmit(){

  }

}
