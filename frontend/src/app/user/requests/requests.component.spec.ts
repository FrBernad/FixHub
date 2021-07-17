import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RequestsComponent} from './requests.component';
import {Title} from "@angular/platform-browser";
import {ContactService} from "../../job/contact/contact.service";
import {TranslateService} from "@ngx-translate/core";
import {of} from "rxjs";
import {TestingModule} from "../../testing.module";
import {ProviderDetails, User} from "../../models/user.model";
import {ContactInfo} from "../../models/contact-info.model";
import {UserService} from "../../auth/services/user.service";
import {MockUserService} from "../provider/dashboard/dashboard.component.spec";


describe('RequestsComponent', () => {
  let component: RequestsComponent;
  let fixture: ComponentFixture<RequestsComponent>;
  let mockTitleService: Title;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RequestsComponent],
      imports: [
        TestingModule
      ],
      providers: [
        {
          provide: UserService,
          useClass:MockUserService
        },
        Title, TranslateService]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestsComponent);
    component = fixture.componentInstance;
    mockTitleService = TestBed.inject(Title);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
