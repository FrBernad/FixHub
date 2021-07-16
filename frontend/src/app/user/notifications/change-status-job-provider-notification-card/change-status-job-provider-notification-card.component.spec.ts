import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ChangeStatusJobProviderNotificationCard} from "./change-status-job-provider-notification-card.component";


describe('ChangeStatusJobProviderNotificationCardComponent', () => {
  let component: ChangeStatusJobProviderNotificationCard;
  let fixture: ComponentFixture<ChangeStatusJobProviderNotificationCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeStatusJobProviderNotificationCard ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeStatusJobProviderNotificationCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
