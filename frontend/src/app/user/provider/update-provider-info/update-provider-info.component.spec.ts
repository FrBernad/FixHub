import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateProviderInfoComponent} from './update-provider-info.component';
import {Router} from "@angular/router";
import {UserService} from "../../../auth/services/user.service";
import {TestingModule} from "../../../testing.module";

describe('UpdateProviderInfoComponent', () => {
  let component: UpdateProviderInfoComponent;
  let fixture: ComponentFixture<UpdateProviderInfoComponent>;
  let mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateProviderInfoComponent],
      imports: [
        TestingModule
      ],
      providers: [
        {
          provide: Router,
          useValue: mockRouter
        },
        UserService
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
