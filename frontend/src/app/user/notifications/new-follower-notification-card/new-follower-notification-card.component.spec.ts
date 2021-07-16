import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewFollowerNotificationCard} from './new-follower-notification-card.component';

describe('NotificationCardComponent', () => {
  let component: NewFollowerNotificationCard;
  let fixture: ComponentFixture<NewFollowerNotificationCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewFollowerNotificationCard ]
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
