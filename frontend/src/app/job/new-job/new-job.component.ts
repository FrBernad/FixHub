import {Router} from '@angular/router';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {JobService} from '../job.service';
import {Subscription} from 'rxjs';
import {User} from 'src/app/models/user.model';
import {UserService} from 'src/app/auth/services/user.service';
import {DiscoverService} from "../../discover/discover.service";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-new-job',
  templateUrl: './new-job.component.html',
  styleUrls: ['./new-job.component.scss','../../shared/choose-state/choose-state.component.scss'],
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

  imagesCounter: number = 0;
  private userSub: Subscription;
  private transSub: Subscription;
  user: User;

  jobId: number;

  allowedImageTypes: string[] = ['image/png', 'image/jpeg'];
  allowedImageType: boolean = true;


  allowedImageSize: boolean = true;
  isFetching = true;


  constructor(
    private jobService: JobService,
    private jobsService: DiscoverService,
    private userService: UserService,
    private router: Router,
    private titleService: Title,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {

    this.changeTitle();

    this.transSub = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.changeTitle();
    });

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
      images: new FormArray([]),
      paused: new FormControl(false),
    });



  }

  changeTitle() {
    this.translateService.get("newJob.title")
      .subscribe(
        (routeTitle) => {
          this.titleService.setTitle('Fixhub | ' + routeTitle)
        }
      )
  }

  onSubmit() {
    if (!this.jobForm.valid) {
      this.jobForm.markAllAsTouched();
      return;
    }
    this.disabled=true;
    let newFormData = new FormData();
    newFormData.append('jobProvided',this.jobForm.get('jobProvided').value);
    newFormData.append('jobCategory', this.jobForm.get('jobCategory').value);
    newFormData.append('price', this.jobForm.get('price').value);
    newFormData.append('description', this.jobForm.get('description').value);
    newFormData.append('paused', this.jobForm.get('paused').value);

    if(this.jobForm.get('images').value.length > 0) {
      this.jobForm.get('images').value.forEach(image => {
        newFormData.append('images', image);
      });
    }

    this.jobService.createJob(newFormData).subscribe((response) =>{
      this.disabled=false;
      let location = response.headers.get('location').split('/');
      this.jobId = +location[location.length-1];
      this.router.navigate(['/jobs', this.jobId]);
    });

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
      return;
    }

    if (file.size > 3000000) {
      this.allowedImageSize = false;
      return;
    }

    if (this.imagesCounter < this.maxImagesPerJob) {
      (<FormArray>this.jobForm.get('images')).push(new FormControl(file));
      this.imagesCounter++;
    }
  }

  deleteImage(index: number) {
    if (index >= 0) {
      (<FormArray>this.jobForm.get('images')).removeAt(index);
      this.imagesCounter--;
    }
  }

  dropdownClick(category: string) {
    this.jobForm.get('jobCategory').setValue(category);
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
    this.transSub.unsubscribe();
  }

}

