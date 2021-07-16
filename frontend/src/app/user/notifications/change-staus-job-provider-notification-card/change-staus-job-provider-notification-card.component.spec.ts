import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeStausJobProviderNotificationCardComponent} from './change-staus-job-provider-notification-card.component';

describe('ChangeStausJobProviderNotificationCardComponent', () => {
  let component: ChangeStausJobProviderNotificationCardComponent;
  let fixture: ComponentFixture<ChangeStausJobProviderNotificationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeStausJobProviderNotificationCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeStausJobProviderNotificationCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
