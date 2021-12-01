import {ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import {DashboardComponent} from './dashboard.component';
import {Title} from "@angular/platform-browser";
import {ContactInfo} from "../../../models/contact-info.model";
import {ProviderDetails, User} from "../../../models/user.model";
import {UserService} from "../../../auth/services/user.service";
import {TranslateService} from "@ngx-translate/core";
import {BehaviorSubject, of} from "rxjs";
import {DiscoverService} from "../../../discover/discover.service";
import {TestingModule} from "../../../testing.module";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";

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
const mockUser = new User(1, 'name', 'surname', 'email', 'phoneNumber', 'state', 'city', 'profileImage', 'converImage', 2, 1, [mockContactInfo], mockProviderDetails);
const mockUserService = {
  user: new BehaviorSubject(mockUser),
  repopulateProviderDetails: () => of({})
}

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let mockJobService: DiscoverService;
  let mockTitleService: Title;
  let mockTranslateService: TranslateService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      imports: [
        TestingModule
      ],
      providers: [
        {provide: UserService, useValue: mockUserService}
        , DiscoverService, Title, TranslateService]
    })
      .compileComponents();
  });

  beforeEach(async () => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    mockJobService = TestBed.inject(DiscoverService);
    mockTranslateService = TestBed.inject(TranslateService);
    mockTitleService = TestBed.inject(Title);
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
});
