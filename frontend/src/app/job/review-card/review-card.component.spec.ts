import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewCardComponent} from './review-card.component';
import {Review} from "../review.model";
import {ContactInfo} from "../../models/contact-info.model";
import {ProviderDetails, User} from "../../models/user.model";
import {TestingModule} from "../../testing.module";

describe('ReviewCardComponent', () => {
  let component: ReviewCardComponent;
  let fixture: ComponentFixture<ReviewCardComponent>;

  const fakeProviderDetails: ProviderDetails = {
  location: {
    cities: [{id: 1, name: ''}],
    state: {id: 1, name: ''}
  },
  schedule: {
    startTime: new Date(),
    endTime: new Date()
  },
  jobsCount: 1,
  avgRating: 2,
  reviewCount: 3,
  contactsCount: 4
};

let fakeContactInfo = new ContactInfo(
  1,
  '',
  '',
  '',
  ''
);

let fakeUser = new User(
  1,
  'name',
  'surname',
  'email',
  'phoneNumber',
  'state',
  'city',
  'profileImage',
  'converImage',
  ['', ''],
  1,
  2,
  [fakeContactInfo],
  fakeProviderDetails,
  true,
  false
);

  let mockReview = new Review(
    1,
    ',',
    2,
    new Date(),
    fakeUser
  )

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewCardComponent ],
        imports: [
        TestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {

    fixture = TestBed.createComponent(ReviewCardComponent);
    component = fixture.componentInstance;
    component.review = mockReview;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
