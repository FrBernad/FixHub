import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChooseStateComponent} from './choose-state.component';
import {DiscoverService} from "../../discover/discover.service";
import {ContactInfo} from "../../models/contact-info.model";
import {ProviderDetails, User} from "../../models/user.model";
import {TestingModule} from "../../testing.module";

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

let mockUser = new User(
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


describe('ChooseStateComponent', () => {
  let component: ChooseStateComponent;
  let fixture: ComponentFixture<ChooseStateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChooseStateComponent],
      imports: [
        TestingModule
      ],
      providers: [
        DiscoverService
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseStateComponent);
    component = fixture.componentInstance;
    component.user=mockUser;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
