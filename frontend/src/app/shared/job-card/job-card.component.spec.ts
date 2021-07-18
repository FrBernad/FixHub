import {ComponentFixture, TestBed} from '@angular/core/testing';

import {JobCardComponent} from './job-card.component';
import {TestingModule} from "../../testing.module";
import {Job} from "../../models/job.model";
import {ProviderDetails, User} from "../../models/user.model";
import {ContactInfo} from "../../models/contact-info.model";

describe('JobCardComponent', () => {
  let component: JobCardComponent;
  let fixture: ComponentFixture<JobCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobCardComponent],
      imports: [
        TestingModule,
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    const mockProviderDetails: ProviderDetails = {
      location: {
        cities: [{id: 1, name: ''}],
        state: {id: 1, name: ''}
      },
      schedule: {
        startTime: new Date(),
        endTime: new Date()
      },
      jobsCount: 1, avgRating: 2, reviewCount: 3, contactsCount: 4
    };
    const mockContactInfo = new ContactInfo(1, '', '', '', '');
    const mockUser = new User(1, 'name', 'surname', 'email', 'phoneNumber', 'state', 'city', 'profileImage', 'converImage', ['', ''], 1, 2, [mockContactInfo], mockProviderDetails, true, false);
    const mockJob = new Job(1, "description", "jobProvided", "category", 3, 3, 4, [], "image", mockUser, false);

    fixture = TestBed.createComponent(JobCardComponent);
    component = fixture.componentInstance;
    component.job = mockJob;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
