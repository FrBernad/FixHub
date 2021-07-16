import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateProviderInfoComponent} from './update-provider-info.component';
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {Router} from "@angular/router";
import {UserService} from "../../../auth/services/user.service";
import {TranslateModule} from "@ngx-translate/core";
import {AppTranslateModule} from "../../../app-translate.module";
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
