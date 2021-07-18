import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateInfoComponent} from './update-info.component';
import {TestingModule} from "../../../../testing.module";
import {ProviderDetails, User} from "../../../../models/user.model";
import {ContactInfo} from "../../../../models/contact-info.model";

describe('UpdateInfoComponent', () => {
  let component: UpdateInfoComponent;
  let fixture: ComponentFixture<UpdateInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateInfoComponent],
      imports: [
        TestingModule
      ],
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
    fixture = TestBed.createComponent(UpdateInfoComponent);
    component = fixture.componentInstance;
    component.loggedUser = mockUser;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
