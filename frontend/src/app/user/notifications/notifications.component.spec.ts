import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NotificationsComponent} from './notifications.component';
import {NotificationsService} from "./notifications.service";
import {TestingModule} from "../../testing.module";

describe('NotificationsComponent', () => {
  let component: NotificationsComponent;
  let fixture: ComponentFixture<NotificationsComponent>;
  let mockNotificationService: NotificationsService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotificationsComponent ],
        imports: [
       TestingModule],
      providers: [NotificationsService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationsComponent);
    mockNotificationService = TestBed.inject(NotificationsService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
