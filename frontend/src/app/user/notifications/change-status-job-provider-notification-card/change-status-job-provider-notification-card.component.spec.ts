import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeStausJobProviderNotificationCard} from './change-status-job-provider-notification-card.component';

describe('ChangeStausJobProviderNotificationCardComponent', () => {
  let component: ChangeStausJobProviderNotificationCard;
  let fixture: ComponentFixture<ChangeStausJobProviderNotificationCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeStausJobProviderNotificationCard ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeStausJobProviderNotificationCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
