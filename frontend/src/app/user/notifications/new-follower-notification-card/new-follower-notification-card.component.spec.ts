import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewFollowerNotificationCard} from './new-follower-notification-card.component';
import {TestingModule} from "../../../testing.module";

describe('NotificationCardComponent', () => {
  let component: NewFollowerNotificationCard;
  let fixture: ComponentFixture<NewFollowerNotificationCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewFollowerNotificationCard ],
      imports:[
        TestingModule
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewFollowerNotificationCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
