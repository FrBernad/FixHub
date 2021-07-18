import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ChangeStatusJobProviderNotificationCard} from "./change-status-job-provider-notification-card.component";
import {NotificationsService} from "../notifications.service";
import {RequestsService} from "../../requests/requests.service";
import {Router} from "@angular/router";
import {TestingModule} from "../../../testing.module";
import {Notification} from "../../../models/notification.model";
import {NotificationType} from "../../../models/notification-type-enum.model";


describe('ChangeStatusJobProviderNotificationCardComponent', () => {
  let component: ChangeStatusJobProviderNotificationCard;
  let fixture: ComponentFixture<ChangeStatusJobProviderNotificationCard>;

  const mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChangeStatusJobProviderNotificationCard],
      imports: [
        TestingModule
      ],
      providers: [
        {
          provide: Router,
          useValue: mockRouter
        },
        NotificationsService, RequestsService],
    })
      .compileComponents();
  });

  beforeEach(() => {
    const mockNotification = new Notification(2, new Date(), 1, false, NotificationType.JOB_REQUEST);

    fixture = TestBed.createComponent(ChangeStatusJobProviderNotificationCard);
    component = fixture.componentInstance;
    component.notification = mockNotification;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
