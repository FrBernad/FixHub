import {ComponentFixture, getTestBed, TestBed} from '@angular/core/testing';

import {NotificationsComponent} from './notifications.component';
import {NotificationsService} from "./notifications.service";
import {TestingModule} from "../../testing.module";
import {UserService} from "../../auth/services/user.service";

describe('NotificationsComponent', () => {
  let component: NotificationsComponent;
  let fixture: ComponentFixture<NotificationsComponent>;
  let mockNotificationService: NotificationsService;
  let injector: TestBed;
  let userService: UserService;



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
    TestBed.configureTestingModule({
      providers: [UserService]
    });
    injector = getTestBed();
    userService = injector.inject(UserService);
    fixture = TestBed.createComponent(NotificationsComponent);
    mockNotificationService = TestBed.inject(NotificationsService);
    component = fixture.componentInstance;
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
