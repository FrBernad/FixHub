import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewPaginationComponent} from './review-pagination.component';
import {ProviderDetails, User} from "../../models/user.model";
import {ContactInfo} from "../../models/contact-info.model";
import {Job} from "../../models/job.model";
import {TestingModule} from "../../testing.module";

describe('ReviewPaginationComponent', () => {
  let component: ReviewPaginationComponent;
  let fixture: ComponentFixture<ReviewPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewPaginationComponent ],
       imports: [
        TestingModule],
      providers:[]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewPaginationComponent);
    let fakeJob = new Job();
    component.job=fakeJob;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
