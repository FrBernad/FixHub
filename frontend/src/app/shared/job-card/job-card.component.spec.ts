import {ComponentFixture, TestBed} from '@angular/core/testing';

import {JobCardComponent} from './job-card.component';
import {TestingModule} from "../../testing.module";
import {Job} from "../../models/job.model";

describe('JobCardComponent', () => {
  let component: JobCardComponent;
  let fixture: ComponentFixture<JobCardComponent>;
  let fakeJob = new Job();

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobCardComponent],
      imports: [
        TestingModule
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobCardComponent);
    component = fixture.componentInstance;
    component.job = fakeJob;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
