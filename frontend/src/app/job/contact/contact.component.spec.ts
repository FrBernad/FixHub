import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ContactComponent} from './contact.component';
import {RequestsService} from "../../user/requests/requests.service";
import {UserService} from "../../auth/services/user.service";
import {TestingModule} from "../../testing.module";
import {ProviderDetails, User} from "../../models/user.model";
import {ContactInfo} from "../../models/contact-info.model";
import {Job} from "../../models/job.model";
import {ReactiveFormsModule} from "@angular/forms";

describe('ContactComponent', () => {
  let component: ContactComponent;
  let fixture: ComponentFixture<ContactComponent>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ContactComponent],
      imports: [
        TestingModule, ReactiveFormsModule
      ],
      providers: [
        RequestsService, UserService
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
    const mockUser = new User(1, 'name', 'surname', 'email', 'phoneNumber', 'state', 'city', 'profileImage', 'converImage', 2, 1, [mockContactInfo], mockProviderDetails);
    const mockJob = new Job(1, "description", "jobProvided", "category", 3, 3, 4, [], "image", mockUser, false);

    fixture = TestBed.createComponent(ContactComponent);
    component = fixture.componentInstance;
    component.job = mockJob;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
