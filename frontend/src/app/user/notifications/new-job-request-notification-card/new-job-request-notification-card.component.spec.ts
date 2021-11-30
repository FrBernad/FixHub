import {ComponentFixture, getTestBed, TestBed} from '@angular/core/testing';

import {NewJobRequestNotificationCardComponent} from './new-job-request-notification-card.component';
import {TestingModule} from "../../../testing.module";
import {NotificationType} from "../../../models/notification-type-enum.model";
import {Notification} from "../../../models/notification.model";
import {UserService} from "../../../auth/services/user.service";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";

describe('NewJobRequestNotificationCardComponent', () => {
  let component: NewJobRequestNotificationCardComponent;
  let fixture: ComponentFixture<NewJobRequestNotificationCardComponent>;
  let injector: TestBed;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewJobRequestNotificationCardComponent],
      schemas:[CUSTOM_ELEMENTS_SCHEMA],
      imports: [TestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {

    TestBed.configureTestingModule({
      providers: [UserService]
    });

    injector = getTestBed();
    userService = injector.inject(UserService);

    const mockNotification = new Notification(2, new Date(), 1, false, NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED);

    fixture = TestBed.createComponent(NewJobRequestNotificationCardComponent);
    component = fixture.componentInstance;
    component.notification = mockNotification;
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
