import {ComponentFixture, getTestBed, TestBed} from '@angular/core/testing';

import {RequestCardComponent} from './request-card.component';
import {TestingModule} from "../../../testing.module";
import {ProviderDetails, User} from "../../../models/user.model";
import {ContactInfo} from "../../../models/contact-info.model";
import {JobStatus} from "../../../models/job-status-enum.model";
import {UserService} from "../../../auth/services/user.service";

describe('RequestCardComponent', () => {
  let component: RequestCardComponent;
  let fixture: ComponentFixture<RequestCardComponent>;
  let injector: TestBed;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestCardComponent ],
      imports:[
        TestingModule
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserService]
    });

    injector = getTestBed();
    userService = injector.inject(UserService);
    const mockContactInfo = new ContactInfo(1, '', '', '', '');
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
    const mockUser = new User(1, 'name', 'surname', 'email', 'phoneNumber', 'state', 'city', 'profileImage', 'converImage', 2, 1, [mockContactInfo]);
    const mockProvider = new User(1, 'name', 'surname', 'email', 'phoneNumber', 'state', 'city', 'profileImage', 'converImage', 2, 1, [mockContactInfo], mockProviderDetails);

    const mockType = 'Type';
    const mockJobRequest = {
      id: 1,
      jobProvided: '',
      jobId: 2,
      message: '',
      status: JobStatus.IN_PROGRESS,
      provider: mockProvider,
      user: mockUser,
      date: new Date(),
      contactInfo: mockContactInfo
    };

    fixture = TestBed.createComponent(RequestCardComponent);
    component = fixture.componentInstance;
    component.type=mockType;
    component.request = mockJobRequest;
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
