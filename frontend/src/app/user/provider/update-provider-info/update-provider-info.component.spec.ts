import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateProviderInfoComponent} from './update-provider-info.component';
import {Router} from "@angular/router";
import {UserService} from "../../../auth/services/user.service";
import {TestingModule} from "../../../testing.module";
import {BehaviorSubject} from "rxjs";
import {User} from "../../../models/user.model";
import {TranslateService} from "@ngx-translate/core";

describe('UpdateProviderInfoComponent', () => {
  let component: UpdateProviderInfoComponent;
  let fixture: ComponentFixture<UpdateProviderInfoComponent>;


  beforeEach(async () => {

    const mockRouter = {
      navigate: jasmine.createSpy('navigate')
    };

    const mockUserService = jasmine.createSpyObj('UserService',
      [],
      {
        user: new BehaviorSubject<User>(new User(
          1, "name", "surname", "email",
          "phoneNumber", "state", "city", "profileImage",
          "coverImage", [], 3, 4))
      }
    );

    await TestBed.configureTestingModule({
      declarations: [UpdateProviderInfoComponent],
      imports: [
        TestingModule,
      ],
      providers: [
        {
          provide: Router,
          useValue: mockRouter
        },
        {
          provide: UserService,
          useValue: mockUserService
        },
        TranslateService
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateProviderInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
