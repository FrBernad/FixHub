import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeStatusJobUserNotificationCardComponent} from './change-status-job-user-notification-card.component';
import {TestingModule} from "../../../testing.module";

describe('ChangeStatusJobUserNotificationCardComponent', () => {
  let component: ChangeStatusJobUserNotificationCardComponent;
  let fixture: ComponentFixture<ChangeStatusJobUserNotificationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeStatusJobUserNotificationCardComponent],
      imports:[
        TestingModule
      ]
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
