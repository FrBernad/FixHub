import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewFollowerNotificationCard} from './new-follower-notification-card.component';
import {TestingModule} from "../../../testing.module";
import {NotificationType} from "../../../models/notification-type-enum.model";
import {LoadingSpinnerComponent} from "../../../shared/loading-spinner/loading-spinner.component";

describe('NotificationCardComponent', () => {
  let component: NewFollowerNotificationCard;
  let fixture: ComponentFixture<NewFollowerNotificationCard>;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewFollowerNotificationCard,
                      LoadingSpinnerComponent],
      imports:[
        TestingModule
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    const mockNotification = jasmine.createSpyObj(
      'notification',
      [],
      {id:1,date:Date.now(),resource:2,seen:false,type:NotificationType.NEW_FOLLOWER}
    )
    fixture = TestBed.createComponent(NewFollowerNotificationCard);
    component = fixture.componentInstance;
    component.notification = mockNotification;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
