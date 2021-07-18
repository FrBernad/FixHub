import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeStatusJobUserNotificationCardComponent} from './change-status-job-user-notification-card.component';
import {TestingModule} from "../../../testing.module";
import {Notification} from "../../../models/notification.model";
import {NotificationType} from "../../../models/notification-type-enum.model";

describe('ChangeStatusJobUserNotificationCardComponent', () => {
  let component: ChangeStatusJobUserNotificationCardComponent;
  let fixture: ComponentFixture<ChangeStatusJobUserNotificationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChangeStatusJobUserNotificationCardComponent],
      imports: [
        TestingModule
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    const mockNotification = new Notification(2, new Date(), 1, false, NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED);

    fixture = TestBed.createComponent(ChangeStatusJobUserNotificationCardComponent);
    component = fixture.componentInstance;
    component.notification = mockNotification;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
