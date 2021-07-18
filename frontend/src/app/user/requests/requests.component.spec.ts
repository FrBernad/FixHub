import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RequestsComponent} from './requests.component';
import {Title} from "@angular/platform-browser";
import {TranslateService} from "@ngx-translate/core";
import {TestingModule} from "../../testing.module";
import {UserService} from "../../auth/services/user.service";
import {ContactInfo} from "../../models/contact-info.model";
import {ProviderDetails, User} from "../../models/user.model";
import {BehaviorSubject} from "rxjs";


describe('RequestsComponent', () => {
  let component: RequestsComponent;
  let fixture: ComponentFixture<RequestsComponent>;
  let mockTitleService: Title;
  const mockProviderDetails: ProviderDetails = {
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
  const mockContactInfo = new ContactInfo(1, '', '', '', '')
  const mockUser = new User(1, 'name', 'surname', 'email', 'phoneNumber', 'state', 'city', 'profileImage', 'converImage', ['', ''], 1, 2, [mockContactInfo], mockProviderDetails, true, false);
  const mockUserService = {
    user: new BehaviorSubject(mockUser)
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RequestsComponent],
      imports: [
        TestingModule
      ],
      providers: [
        {
          provide: UserService,
          useValue: mockUserService
        },
        Title, TranslateService]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestsComponent);
    component = fixture.componentInstance;
    mockTitleService = TestBed.inject(Title);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
