import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.scss']
})
export class ReviewFormComponent implements OnInit {

  rates: number[] = [1, 2, 3, 4, 5]
  reviewForm: FormGroup;
  minDescLength: number = 4;
  maxDescLength: number = 300;
  minRate: number = 1;
  maxRate: number = 5;


  constructor() {
  }

  ngOnInit(): void {
    //key value pairs
    this.reviewForm = new FormGroup({
      'description': new FormControl(null, [Validators.required, Validators.minLength(this.minDescLength), Validators.maxLength(this.maxDescLength)]),//initial values, validators,async validators
      'rating': new FormControl("1", [Validators.required, Validators.min(this.minRate), Validators.max(this.maxRate)])
    })

  }

  onSubmit() {
    if(!this.reviewForm.valid){
      this.reviewForm.markAllAsTouched();
      return;
    }

  }

}
