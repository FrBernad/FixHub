import {ContactInfo} from '../../models/contact-info.model';
import {RequestsService} from '../../user/requests/requests.service';
import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../models/user.model';
import {Job} from '../../models/job.model';
import {Subscription} from 'rxjs';
import {UserService} from 'src/app/auth/services/user.service';
import {Router} from "@angular/router";


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent implements OnInit, OnDestroy {
  contactForm: FormGroup;

  @Input("job") job: Job;

  jobId: number;
  isFetching: boolean = true;

  maxStateLength: number = 50;
  maxCityLength: number = 50;
  maxStreetLength: number = 50;
  maxMessageLength: number = 300;
  maxFloorLength: number = 9;
  maxAddressNumber: number = 9;
  maxDepartmentLength: number = 30;
  newContactInfo = true;

  private userSub: Subscription;
  user: User;
  disabled = false;
  modal: any;

  constructor(
    private contactService: RequestsService,
    private userService: UserService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.modal = new bootstrap.Modal(document.getElementById("contactModal"));


    this.userSub = this.userService.user.subscribe((user) => {
      this.user = user;
    });

    this.jobId = this.job.id;

    this.initForm();
  }

  onSubmit() {

    if (!this.contactForm.valid) {
      this.contactForm.markAllAsTouched();
      return;
    }

    this.disabled = true

    this.contactService.newContact(this.jobId, {
      state: this.job.provider.providerDetails.location.state.name,
      city: this.contactForm.get('city').value,
      street: this.contactForm.get('street').value,
      floor: this.contactForm.get('floor').value,
      departmentNumber: this.contactForm.get('departmentNumber').value,
      message: this.contactForm.get('message').value,
      contactInfoId: this.contactForm.get('contactInfoId').value,
      userId: '' + this.user.id,
      addressNumber: this.contactForm.get('addressNumber').value,
    }).subscribe(
      (contactId) => {
        this.router.navigate(["/user/requests/sent/" + contactId]);
        this.modal.hide();
        this.onClose();
        this.disabled = false;
      }
    );
  }

  dropdownClickCity(city: { id: number; name: string }) {
    this.contactForm.get('city').setValue(city.name);
  }

  dropdownClickContact(contact: ContactInfo) {
    this.contactForm.get('contactInfoId').setValue(contact.id);
    this.contactForm.get('addressNumber').setValue(contact.addressNumber);
    this.contactForm.get('city').setValue(contact.city);
    this.contactForm.get('departmentNumber').setValue(contact.departmentNumber);
    this.contactForm.get('floor').setValue(contact.floor);
    this.contactForm.get('street').setValue(contact.street);
    this.newContactInfo = false;
  }

  resetInfo() {
    this.contactForm.get('contactInfoId').setValue(-1);
    this.contactForm.get('addressNumber').setValue(null);
    this.contactForm.get('city').setValue(null);
    this.contactForm.get('departmentNumber').setValue(null);
    this.contactForm.get('floor').setValue(null);
    this.contactForm.get('street').setValue(null);
    this.newContactInfo = true;
  }

  onClose() {
    this.resetInfo();
    this.contactForm.get('message').setValue(null);
    this.contactForm.markAsUntouched();
  }

  cityNotIncluded() {
    const formCity = this.contactForm.get('city').value;
    return (
      formCity != null &&
      formCity != '' &&
      !this.job.provider.providerDetails.location.cities.some(
        (city) => city.name === formCity
      )
    );
  }

  getCity() {
    return this.contactForm.get('city').value;
  }

  private initForm() {
    this.contactForm = new FormGroup({
      state: new FormControl(
        this.job.provider.providerDetails.location.state.name,
        [
          Validators.required,
          Validators.maxLength(this.maxStateLength),
          Validators.pattern(
            "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
          ),
        ]
      ),
      city: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxCityLength),
        Validators.pattern(
          "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
        ),
      ]),
      street: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxStreetLength),
        Validators.pattern(
          "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$"
        ),
      ]),
      addressNumber: new FormControl(null, [
        Validators.required,
        Validators.pattern('[0-9]{0,9}'),
      ]),
      floor: new FormControl('', Validators.pattern('[0-9]{0,9}')),
      departmentNumber: new FormControl('', [
        Validators.pattern('[A-Za-z0-9]{0,30}'),
        Validators.maxLength(this.maxDepartmentLength),
      ]),
      message: new FormControl(null, [
        Validators.required,
        Validators.maxLength(this.maxMessageLength),
      ]),
      contactInfoId: new FormControl(-1, Validators.required),
    });
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }

}
