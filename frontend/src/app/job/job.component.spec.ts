import {ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import {JobComponent} from './job.component';
import {Title} from "@angular/platform-browser";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../auth/services/user.service";
import {JobService} from "./job.service";
import {MockActivatedRoute} from "./edit-job/edit-job.component.spec";
import {TestingModule} from "../testing.module";
import {JobModule} from "./job.module";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";


describe('JobComponent', () => {
  let component: JobComponent;
  let fixture: ComponentFixture<JobComponent>;
  let mockJobService: JobService;
  let mockUserService: UserService;
  let mockRoute = new MockActivatedRoute({'id': 1});
  let mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };
  let mockTitleService: Title;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      imports: [
        TestingModule,
        JobModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {snapshot: {params: {'id': 1}}}
        },
        {
          provide: Router,
          useValue: mockRouter
        },
        JobService, UserService, Title]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobComponent);
    mockRoute = TestBed.inject(ActivatedRoute);
    mockTitleService = TestBed.inject(Title);
    mockUserService = TestBed.inject(UserService);
    mockJobService = TestBed.inject(JobService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    expect(component).toBeTruthy();
  }));
});
