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
  styleUrls: ['./new-job.component.scss'],
})
export class NewJobComponent implements OnInit, OnDestroy {
  categories: string[] = [
    'CARPINTERO',
    'CATERING',
    'CHEF',
    'ELECTRICISTA',
    'ENTREGA',
    'FOTOGRAFO',
    'FUMIGADOR',
    'GASISTA',
    'HERRERO',
    'JARDINERO',
    'LIMPIEZA',
    'CUIDADOR_DE_ANCIANO',
    'MANTENIMIENTO',
    'MECANICO',
    'MUDANZA',
    'NINERA',
    'PASEADOR_DE_PERRO',
    'PLOMERO',
    'PINTOR',
    'TECHISTA',
    'VIDRIERO',
  ];

  jobForm: FormGroup;

  maxImagesPerJob: number = 6;
  maxJobProvidedLength: number = 50;
  maxDescriptionLength: number = 300;
  minPrice: number = 1;
  maxPrice: number = 999999;
  disabled=false;

  private userSub: Subscription;
  user: User;

  allowedImageTypes: string[] = ['image/png', 'image/jpeg'];
  allowedImageType: boolean = true;

  maxImagesReached: boolean = false;

  allowedImageSize: boolean = true;
  isFetching = true;

  imagesArray = new FormArray([]);

  constructor(
    private jobService: JobService,
    private userService: UserService,
    private jobsSerivce : JobsService
  ) {}

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe((user) => {
      this.user = user;
    });
    this.jobsSerivce.getCategories().subscribe(
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

    console.log(this.jobForm.value);

    this.jobService
      .createJob({
        providerId: this.user.id,
        ...this.jobForm.value,
      }).subscribe((response) => {
        console.log(response);
       this.disabled=false;
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

