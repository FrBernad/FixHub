import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import {JoinComponent} from './join.component';
import {Title} from "@angular/platform-browser";
import {AuthService} from "../services/auth.service";
import {UserService} from "../services/user.service";
import {TranslateService} from "@ngx-translate/core";
import {BehaviorSubject, of} from "rxjs";
import {Router} from "@angular/router";
import {TestingModule} from "../../testing.module";
import {ProviderDetails, User} from "../../models/user.model";
import {ContactInfo} from "../../models/contact-info.model";

describe('JoinComponent', () => {
  let component: JoinComponent;
  let fixture: ComponentFixture<JoinComponent>;
  let mockAuthService: AuthService;
  let mockTitleService: Title;
  let mockTranslateService: TranslateService;
  const mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

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
      declarations: [JoinComponent],
      imports: [
        TestingModule
      ],
      providers: [
        {provide: UserService, useValue: mockUserService},
        {
          provide: Router,
          useValue: mockRouter
        },
        AuthService, TranslateService, Title]
    })
      .compileComponents();
  });

  beforeEach(async () => {
    fixture = TestBed.createComponent(JoinComponent);
    mockAuthService = TestBed.inject(AuthService);
    mockTitleService = TestBed.inject(Title);
    mockTranslateService = TestBed.inject(TranslateService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    spyOn(mockTranslateService, 'get').and
      .returnValue(of('mockString'));
    spyOn(mockTranslateService.get('mockString'), 'subscribe');
    component.ngOnInit();
    tick();
    expect(component).toBeTruthy();
  }));
});
