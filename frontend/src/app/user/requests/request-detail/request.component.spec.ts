import {ComponentFixture, getTestBed, TestBed} from '@angular/core/testing';

import {RequestComponent} from './request-detail.component';
import {TestingModule} from "../../../testing.module";
import {UserService} from "../../../auth/services/user.service";

describe('RequestComponent', () => {
  let component: RequestComponent;
  let fixture: ComponentFixture<RequestComponent>;
  let userService: UserService;
  let injector: TestBed;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestComponent ],
      imports:[
          TestingModule
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ UserService ]
    })
    injector = getTestBed();
    userService = injector.inject(UserService);
    fixture = TestBed.createComponent(RequestComponent);
    component = fixture.componentInstance;
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
