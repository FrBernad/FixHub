import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeStatusJobUserNotificationCardComponent} from './change-status-job-user-notification-card.component';

describe('ChangeStatusJobUserNotificationCardComponent', () => {
  let component: ChangeStatusJobUserNotificationCardComponent;
  let fixture: ComponentFixture<ChangeStatusJobUserNotificationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeStatusJobUserNotificationCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeStatusJobUserNotificationCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
