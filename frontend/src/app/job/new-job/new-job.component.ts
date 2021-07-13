import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { JobService } from '../job.service';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/auth/user.service';
import {JobsService} from "../../discover/jobs.service";

@Component({
  selector: 'app-new-job',
  templateUrl: './new-job.component.html',
  styleUrls: ['./new-job.component.scss','../../join/choose-state/choose-state.component.scss'],
})
export class NewJobComponent implements OnInit, OnDestroy {
  categories: string[] = [];

  jobForm: FormGroup;

  maxImagesPerJob: number = 6;
  maxJobProvidedLength: number = 50;
  maxDescriptionLength: number = 300;
  minPrice: number = 1;
  maxPrice: number = 999999;
  disabled=false;

  private userSub: Subscription;
  user: User;

  jobId: number;

  allowedImageTypes: string[] = ['image/png', 'image/jpeg'];
  allowedImageType: boolean = true;


  allowedImageSize: boolean = true;
  isFetching = true;

  imagesArray = new FormArray([]);

  constructor(
    private jobService: JobService,
    private jobsService: JobsService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe((user) => {
      this.user = user;
    });
    this.jobsService.getCategories().subscribe(
      (responseData) => {
          responseData.values.forEach( (category) =>{this.categories.push(category);})
          this.isFetching = false;
      }
    )

    this.jobForm = new FormGroup({
      jobProvided: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxJobProvidedLength),
        Validators.pattern(
          "^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
        ),
      ]),
      jobCategory:  new FormControl(null, [Validators.required]),
      price: new FormControl(null, [
        Validators.required,
        Validators.min(this.minPrice),
        Validators.max(this.maxPrice),
      ]),
      description: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxDescriptionLength),
      ]),
      images: this.imagesArray,
      paused: new FormControl(false),
    });
  }

  onSubmit() {
    if (!this.jobForm.valid) {
      this.jobForm.markAllAsTouched();
      return;
    }
    this.disabled=true;

    this.jobService
      .createJob({
        jobProvided: this.jobForm.get('jobProvided').value,
        jobCategory: this.jobForm.get('jobCategory').value,
        price: this.jobForm.get('price').value,
        description: this.jobForm.get('description').value,
        paused: this.jobForm.get('paused').value
      }).subscribe((response) => {
        let location = response.headers.get('location').split('/');
        this.jobId = +location[location.length-1];
        if(this.imagesArray.value.length > 0) {
          let formData = new FormData;
          this.imagesArray.value.forEach(image => {
            formData.append(image.name, image);
            console.log(formData.get(image.name));
          });
          console.log(formData);
          this.jobService.addJobImages(this.jobId, formData).subscribe((response) => {
            console.log(response);
          })
        }
        this.router.navigate(['/jobs', this.jobId]);
      });


  }

  onFileChanged(event) {
    const file = event.target.files[0];
    this.allowedImageType = true;
    this.allowedImageSize = true;

    if (!this.allowedImageTypes.includes(file.type)) {
      this.allowedImageType = false;
      return;
    }

    if (file.size > 3000000) {
      this.allowedImageSize = false;
      return;
    }

    if (this.imagesArray.length < this.maxImagesPerJob) {
      (<FormArray>this.jobForm.get('images')).push(new FormControl(file));
      console.log(this.imagesArray);
    }
  }

  deleteImage(index: number) {
    if (index >= 0) {
      console.log(this.imagesArray[index]);
      this.imagesArray.removeAt(index);
    }
  }

  dropdownClick(category: string) {
    this.jobForm.get('jobCategory').setValue(category);
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }

}

