import { Component, OnInit } from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../models/user.model";
import {Job} from "../../models/job.model";

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

  allowedImageTypes: string[] = ['image/png', 'image/jpeg'];
  allowedImageType: boolean = true;

  maxImagesReached: boolean = false;

  allowedImageSize: boolean = true;

  imagesArray = new FormArray([]);

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
      'description': new FormControl(null, [Validators.required, Validators.maxLength(this.maxDescriptionLength)]),
      images: this.imagesArray
    })
  }

  onSubmit(){
    console.log(this.editJobForm);
  }

  onFileChanged(event) {
    const file = event.target.files[0];
    this.allowedImageType = true;
    this.allowedImageSize = true;

    if(!this.allowedImageTypes.includes(file.type)) {
      this.allowedImageType = false;
      return;
    }

    if(file.size > 3000000) {
      this.allowedImageSize = false;
      return
    }

    if (this.imagesArray.length < this.maxImagesPerJob) {
      (<FormArray>this.editJobForm.get('images')).push(
        new FormControl(file)
      );
      console.log(this.imagesArray);
    }
  }

  deleteImage(index: number) {
    if (index >= 0) {
      console.log(this.imagesArray[index]);
      this.imagesArray.removeAt(index);
    }
  }

}
