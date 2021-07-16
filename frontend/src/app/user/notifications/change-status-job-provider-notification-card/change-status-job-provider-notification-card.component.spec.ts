import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ChangeStatusJobProviderNotificationCard} from "./change-status-job-provider-notification-card.component";
import {NotificationsService} from "../notifications.service";
import {ContactService} from "../../../job/contact/contact.service";
import {Router} from "@angular/router";
import {TestingModule} from "../../../testing.module";


describe('ChangeStatusJobProviderNotificationCardComponent', () => {
  let component: ChangeStatusJobProviderNotificationCard;
  let fixture: ComponentFixture<ChangeStatusJobProviderNotificationCard>;
  let mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeStatusJobProviderNotificationCard ],
      imports:[
        TestingModule
      ],
      providers:[
        {
          provide: Router,
          useValue: mockRouter
        },
        NotificationsService, ContactService],
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

let mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };
