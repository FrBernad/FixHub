import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewJobRequestNotificationCardComponent} from './new-job-request-notification-card.component';
import {TestingModule} from "../../../testing.module";
import {NotificationType} from "../../../models/notification-type-enum.model";
import {Notification} from "../../../models/notification.model";

describe('NewJobRequestNotificationCardComponent', () => {
  let component: NewJobRequestNotificationCardComponent;
  let fixture: ComponentFixture<NewJobRequestNotificationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewJobRequestNotificationCardComponent],
      imports: [TestingModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    const mockNotification = new Notification(2, new Date(), 1, false, NotificationType.REQUEST_STATUS_CHANGE_USER_ACCEPTED);

    fixture = TestBed.createComponent(NewJobRequestNotificationCardComponent);
    component = fixture.componentInstance;
    component.notification = mockNotification;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
