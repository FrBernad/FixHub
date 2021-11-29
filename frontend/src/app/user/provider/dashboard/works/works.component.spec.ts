import {ComponentFixture, getTestBed, TestBed} from '@angular/core/testing';

import {WorksComponent} from './works.component';
import {TestingModule} from "../../../../testing.module";
import {UserService} from "../../../../auth/services/user.service";

describe('WorksComponent', () => {
  let injector: TestBed;
  let userService: UserService;
  let component: WorksComponent;
  let fixture: ComponentFixture<WorksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorksComponent ],
        imports: [
        TestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserService]
    });

    injector = getTestBed();
    userService = injector.inject(UserService);
    fixture = TestBed.createComponent(WorksComponent);
    component = fixture.componentInstance;
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
