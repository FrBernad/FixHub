import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewPaginationComponent} from './review-pagination.component';
import {TestingModule} from "../../testing.module";
import {Job} from "../../models/job.model";

describe('ReviewPaginationComponent', () => {
  let component: ReviewPaginationComponent;
  let fixture: ComponentFixture<ReviewPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewPaginationComponent],
      imports: [
        TestingModule],
      providers: []
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewPaginationComponent);
    component = fixture.componentInstance;
    component.job = new Job(1, "description", "jobProvided", "category", 3, 3, 4, [], "image", undefined, false);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
