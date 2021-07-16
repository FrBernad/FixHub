import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewJobRequestNotificationCardComponent} from './new-job-request-notification-card.component';

describe('NewJobRequestNotificationCardComponent', () => {
  let component: NewJobRequestNotificationCardComponent;
  let fixture: ComponentFixture<NewJobRequestNotificationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewJobRequestNotificationCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewJobRequestNotificationCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
