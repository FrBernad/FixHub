import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import {JoinComponent} from './join.component';
import {Title} from "@angular/platform-browser";
import {AuthService} from "../services/auth.service";
import {UserService} from "../services/user.service";
import {TranslateService} from "@ngx-translate/core";
import {MockUserService} from "../../user/provider/dashboard/dashboard.component.spec";
import {of} from "rxjs";
import {Router} from "@angular/router";
import {TestingModule} from "../../testing.module";

describe('JoinComponent', () => {
  let component: JoinComponent;
  let fixture: ComponentFixture<JoinComponent>;
  let mockAuthService: AuthService;
    let mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };
  let mockTitleService: Title;
  let mockTranslateService: TranslateService;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      declarations: [JoinComponent],
      imports: [
       TestingModule
      ],
      providers: [
        {provide: UserService, useClass:MockUserService},
        {
          provide: Router,
          useValue: mockRouter
        },
        AuthService,TranslateService,Title]
    })
      .compileComponents();
  });

  beforeEach(async () => {
    fixture = TestBed.createComponent(JoinComponent);
    mockAuthService = TestBed.inject(AuthService);
    mockTitleService = TestBed.inject(Title);
    mockTranslateService = TestBed.inject(TranslateService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    spyOn(mockTranslateService,'get').and
      .returnValue(of('mockString'));
    spyOn(mockTranslateService.get('mockString'),'subscribe');
    component.ngOnInit();
    tick();
    expect(component).toBeTruthy();
  }));
});
