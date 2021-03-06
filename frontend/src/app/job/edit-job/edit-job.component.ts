import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {JobService} from "../job.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {SingleJob} from "../../models/single-job.model";
import {Title} from "@angular/platform-browser";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-edit-job',
  templateUrl: './edit-job.component.html',
  styleUrls: ['./edit-job.component.scss', '../new-job/new-job.component.scss', '../job.component.scss']
})
export class EditJobComponent implements OnInit, OnDestroy {

  maxJobProvidedLength: number = 50;
  maxDescriptionLength: number = 300;
  minPrice: number = 1;
  maxPrice: number = 999999;
  editJobForm: FormGroup;
  selectedIndex = 0;
  allowedImageTypes: string[] = ['image/png', 'image/jpeg'];
  imagesCounter: number;

  isFetching = true;
  disabled = false;

  allowedImageType: boolean = true;
  allowedImageSize: boolean = true;

  private transSub: Subscription;

  job: SingleJob = new SingleJob(1, "description", "jobProvided", "category", 3, 3, 4, [], "image", undefined, false, true);

  maxImagesPerJob: number = 6;

  constructor(private jobService: JobService,
              private route: ActivatedRoute,
              private router: Router,
              private titleService: Title,
              private translateService: TranslateService) {
  }


  selectPrevious() {
    if (this.selectedIndex == 0) {
      this.selectedIndex = this.job.imagesUrls.length - 1;
    } else {
      this.selectedIndex--;
    }
  }

  selectNext() {
    if (this.selectedIndex == this.job.imagesUrls.length - 1) {
      this.selectedIndex = 0;
    } else {
      this.selectedIndex++;
    }
  }


  ngOnInit(): void {

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });

    this.route.params.subscribe(
      (params: Params) => {
        this.job.id = params['id'];
      }
    );

    this.editJobForm = new FormGroup({
      'jobProvided': new FormControl(this.job.jobProvided, [Validators.required, Validators.maxLength(this.maxJobProvidedLength), Validators.pattern("^[a-zA-Z0-9??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? ,.'-]*$")]),
      'jobCategory': new FormControl(this.job.category, [Validators.required]),
      'price': new FormControl(this.job.price, [Validators.required, Validators.min(this.minPrice), Validators.max(this.maxPrice)]),
      'description': new FormControl(this.job.description, [Validators.required, Validators.maxLength(this.maxDescriptionLength)]),
      'paused': new FormControl(this.job.paused),
      'imagesIdToDelete': new FormArray([]),
      'imagesToUpload': new FormArray([])
    })

    this.jobService.getJob(+this.job.id).subscribe(
      responseData => {
        this.updateView(responseData);
      }
    );


  }

  changeTitle() {
    this.translateService.get("editJob.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  onSubmit() {

    if (!this.editJobForm.valid) {
      this.editJobForm.markAllAsTouched();
      return;
    }

    this.disabled = true;
    let newFormData = new FormData();

    this.editJobForm.get('imagesIdToDelete').value.forEach(
      (id) => {
        newFormData.append('imagesIdToDelete', id);
      }
    );

    newFormData.append('jobProvided', this.editJobForm.get('jobProvided').value);
    newFormData.append('price', this.editJobForm.get('price').value);
    newFormData.append('description', this.editJobForm.get('description').value);
    newFormData.append('paused', this.editJobForm.get('paused').value);

    if (this.editJobForm.get('imagesToUpload').value.length > 0) {
      this.editJobForm.get('imagesToUpload').value.forEach(image => {
        newFormData.append('images', image);
      });
    }

    this.jobService.updateJob(this.job.id, newFormData).subscribe((response) => {
      this.isFetching = true;
      this.jobService.getJob(+this.job.id).subscribe(
        responseData => {
          this.disabled = false;
          this.updateView(responseData);
          this.router.navigate(['jobs', this.job.id]);

        }
      );
    })

  }

  onFileChanged(event) {
    const file = event.target.files[0];
    this.allowedImageType = true;
    this.allowedImageSize = true;

    if(!file) {
      return;
    }

    if (!this.allowedImageTypes.includes(file.type)) {
      this.allowedImageType = false;
      setTimeout(() => {
        this.allowedImageType = true;
      }, 2000)
      return;
    }

    if (file.size > 3000000) {
      this.allowedImageSize = false;
      setTimeout(() => {
        this.allowedImageSize = true;
      }, 2000)
      return
    }

    if (this.imagesCounter < this.maxImagesPerJob) {
      (<FormArray>this.editJobForm.get('imagesToUpload')).push(
        new FormControl(file)
      );
      this.imagesCounter++;
    }
  }

  deleteImage(index: number, image: string) {
    let aux = image.split('/');
    if (index >= 0) {
      (<FormArray>this.editJobForm.get('imagesIdToDelete')).push(new FormControl(aux[aux.length - 1]))
      this.job.imagesUrls.splice(index, 1);
      this.imagesCounter--;
    }
  }

  deleteUploadImage(index: number) {
    if (index >= 0) {
      (<FormArray>this.editJobForm.get('imagesToUpload')).removeAt(index);
      this.imagesCounter--;
    }
  }

  private updateView(responseData) {

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
    this.job.imagesUrls = responseData.imagesUrls;
    (<FormArray>this.editJobForm.get('imagesToUpload')).clear();
    (<FormArray>this.editJobForm.get('imagesIdToDelete')).clear();

    this.job.paused = responseData.paused;
    this.editJobForm.patchValue({paused: responseData.paused});
    this.job.thumbnailImageUrl = responseData.thumbnailImage;

    this.imagesCounter = this.job.imagesUrls.length;
    this.isFetching = false;
  }

  getImagesToUpload() {
    return (<FormArray>this.editJobForm.get('imagesToUpload')).controls;
  }

  ngOnDestroy(): void {
    this.transSub.unsubscribe();
  }
}
