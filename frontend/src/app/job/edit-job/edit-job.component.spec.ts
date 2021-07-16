import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import {EditJobComponent} from './edit-job.component';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {JobService} from "../job.service";
import {Observable, of} from "rxjs";
import {TestingModule} from "../../testing.module";


export class MockActivatedRoute extends ActivatedRoute {
  params: Observable<Params>;

  constructor(parameters?: { [key: string]: any; }) {
    super();
    this.params = of(parameters);
  }
}

describe('EditJobComponent', () => {
  let component: EditJobComponent;
  let fixture: ComponentFixture<EditJobComponent>;
  let mockJobService: JobService;
  let mockRoute = new MockActivatedRoute({'id': 1});
  let mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditJobComponent],
      imports: [
        TestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: mockRoute
        },
        {
          provide: Router,
          useValue: mockRouter
        },
        JobService]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditJobComponent);
    component = fixture.componentInstance;
    mockJobService = TestBed.inject(JobService);
    mockRoute = TestBed.inject(ActivatedRoute);
    fixture.detectChanges();
  });

  it('should create', fakeAsync(() => {
    component.ngOnInit();
    tick();
    expect(component).toBeTruthy();
  }));
});
