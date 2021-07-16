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
    component = fixture.componentInstance;
    const fakeProviderDetails: ProviderDetails={
      location:{
        cities:[{id:1,name:''}],
        state:{id:1,name:''}
      },
      schedule:{
        startTime: new Date(),
        endTime: new Date()
      },
      jobsCount:1,
      avgRating:2,
      reviewCount:3,
      contactsCount:4
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
      ['',''],
      1,
      2,
      [fakeContactInfo],
      fakeProviderDetails,
      true,
      false
    );

    let fakeJob = new Job(
      1,
      '',
      '',
      '',
      1,
      2,
      3,
      [],
      '',
      fakeUser,
      false,
    );

    component.job=fakeJob;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
