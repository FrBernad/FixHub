import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {Job} from "../../models/job.model";
import {JobService} from "../job.service";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
  selector: 'app-edit-job',
  templateUrl: './edit-job.component.html',
  styleUrls: ['./edit-job.component.scss', '../new-job/new-job.component.scss', '../job.component.scss']
})
export class EditJobComponent implements OnInit {

  maxJobProvidedLength: number = 50;
  maxDescriptionLength: number = 300;
  minPrice: number = 1;
  maxPrice: number = 999999;
  editJobForm: FormGroup;
  selectedIndex = 0;
  isFetching = true;
  disabled = false;
  allowedImageTypes: string[] = ['image/png', 'image/jpeg'];

  allowedImageType: boolean = true;
  maxImagesReached: boolean = false;
  allowedImageSize: boolean = true;

  imagesArray = new FormArray([]);
  imagesToDeleteArray = new FormArray([]);
  imagesToUploadArray = new FormArray([]);


  job: Job = new Job();

  maxImagesPerJob: number = 6;

  constructor(private jobService: JobService, private route: ActivatedRoute) {
  }


  selectPrevious() {
    if (this.selectedIndex == 0) {
      this.selectedIndex = this.job.images.length - 1;
    } else {
      this.selectedIndex--;
    }
  }

  selectNext() {
    if (this.selectedIndex == this.job.images.length - 1) {
      this.selectedIndex = 0;
    } else {
      this.selectedIndex++;
    }
  }


  ngOnInit(): void {

    this.route.params.subscribe(
      (params: Params) => {
        this.job.id = params['id'];
      }
    );

    this.editJobForm = new FormGroup({
      'jobProvided': new FormControl(this.job.jobProvided, [Validators.required, Validators.maxLength(this.maxJobProvidedLength), Validators.pattern("^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$")]),
      'jobCategory': new FormControl(this.job.category, [Validators.required]),
      'price': new FormControl(this.job.price, [Validators.required, Validators.min(this.minPrice), Validators.max(this.maxPrice)]),
      'description': new FormControl(this.job.description, [Validators.required, Validators.maxLength(this.maxDescriptionLength)]),
      'paused': new FormControl(this.job.paused),
      'images': this.imagesArray,
      'imagesToDelete': this.imagesToDeleteArray,
      'imagesToUpload': this.imagesToUploadArray
    })

    this.jobService.getJob(+this.job.id).subscribe(
      responseData => {
          this.updateView(responseData);
      }
    );


  }

  onSubmit() {
    console.log(this.editJobForm.get('imagesToDelete'));

    if (!this.editJobForm.valid) {
      this.editJobForm.markAllAsTouched();
      return;
    }

    this.disabled = true;
    let newFormData = new FormData();

    this.editJobForm.get('imagesToDelete').value.forEach(
      (url) => {
        let aux = url.split('/');
        newFormData.append('imagesIdToDelete', aux[aux.length - 1]);
      }
    );

    newFormData.append('jobProvided', this.editJobForm.get('jobProvided').value);
    newFormData.append('price', this.editJobForm.get('price').value);
    newFormData.append('description', this.editJobForm.get('description').value);
    newFormData.append('paused', this.editJobForm.get('paused').value);

    if (this.imagesToUploadArray.value.length > 0) {
      this.imagesToUploadArray.value.forEach(image => {
        newFormData.append('images', image);
      });
    }

    this.jobService.updateJob(this.job.id, newFormData).subscribe((response) => {
      this.isFetching= true;
      this.jobService.getJob(+this.job.id).subscribe(
        responseData => {
          this.updateView(responseData);
        }
      );
      this.disabled = false;
    })

    /*
     Agregar luego de hacer el pedido al servicio
     this.disabled=false;
    */
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
      return
    }

    if (this.imagesArray.length < this.maxImagesPerJob) {
      (<FormArray>this.editJobForm.get('imagesToUpload')).push(
        new FormControl(file)
      );
    }
  }

  deleteImage(index: number, image: string) {
    if (index >= 0) {
      this.imagesArray.removeAt(index);
      this.imagesToDeleteArray.push(new FormControl(image));
      this.job.images = this.imagesArray.value;
    }
  }

  deleteUploadImage(index: number) {
    if (index >= 0) {
      this.imagesToUploadArray.removeAt(index);
    }
  }

  private updateView(responseData){
    this.job.jobProvided = responseData.jobProvided;
    this.editJobForm.patchValue({jobProvided: responseData.jobProvided});
    this.job.description = responseData.description;
    this.editJobForm.patchValue({description: responseData.description});
    this.job.category = responseData.category;
    this.editJobForm.patchValue({jobCategory: responseData.category});
    this.job.price = responseData.price;
    this.editJobForm.patchValue({price: responseData.price});
    this.job.provider = responseData.provider;
    this.job.totalRatings = responseData.totalRatings;
    this.job.averageRating = responseData.averageRating;
    this.job.images = responseData.images;
    this.editJobForm.get('imagesToUpload').patchValue(responseData.images);
    this.job.paused = responseData.paused;
    this.editJobForm.patchValue({paused: responseData.paused});
    this.job.thumbnailImage = responseData.thumbnailImage;
    this.job.images.forEach(image => {
      (<FormArray>this.editJobForm.get('images')).push(
        new FormControl(image)
      );
    });
    this.isFetching = false;
  }
}
