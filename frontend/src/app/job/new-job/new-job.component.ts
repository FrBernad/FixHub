import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-job',
  templateUrl: './new-job.component.html',
  styleUrls: ['./new-job.component.scss'],
})
export class NewJobComponent implements OnInit {
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

  allowedImageTypes: string[] = ['image/png', 'image/jpeg'];
  allowedImageType: boolean = true;

  maxImagesReached: boolean = false;

  allowedImageSize: boolean = true;

  imagesArray = new FormArray([]);
  jobCategory = new FormControl(null, [Validators.required]);

  constructor() {}

  ngOnInit(): void {
    this.jobForm = new FormGroup({
      jobProvided: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxJobProvidedLength),
        Validators.pattern(
          "^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
        ),
      ]),
      jobCategory: this.jobCategory,
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
    });
  }

  onSubmit() {
    console.log(this.jobForm);
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
      (<FormArray>this.jobForm.get('images')).push(
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

  dropdownClick(category: string) {
    this.jobCategory.setValue(category);
  }

}
