import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {JobService} from "../job.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.scss']
})
export class ReviewFormComponent implements OnInit {

  @Input() jobId: number;
  rates: number[] = [1, 2, 3, 4, 5]
  reviewForm: FormGroup;
  minDescLength: number = 4;
  maxDescLength: number = 300;
  minRate: number = 1;
  maxRate: number = 5;
  disabled = false;

  modal: any;

  constructor(
    private jobService: JobService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    //key value pairs
    this.modal = new bootstrap.Modal(document.getElementById("staticBackdrop"));
    this.reviewForm = new FormGroup({
      'description': new FormControl(null, [Validators.required, Validators.minLength(this.minDescLength), Validators.maxLength(this.maxDescLength)]),//initial values, validators,async validators
      'rating': new FormControl("1", [Validators.required, Validators.min(this.minRate), Validators.max(this.maxRate)])
    })
  }

  onSubmit() {
    if (!this.reviewForm.valid) {
      this.reviewForm.markAllAsTouched();
      return;
    }

    this.disabled = true;

    this.jobService.createReview({
      description: this.reviewForm.get('description').value,
      rating: this.reviewForm.get('rating').value
    }, this.jobId).subscribe(() => {
      this.jobService.updateReviews(this.jobId);
      this.disabled = false;
    });

    this.modal.hide();
    this.cleanReviewForm()
  }

  onClose() {
    this.cleanReviewForm();
  }

  private cleanReviewForm() {
    this.reviewForm.markAsUntouched();
    this.reviewForm.get('description').patchValue('');
    this.reviewForm.get('rating').patchValue('1');
  }
}
