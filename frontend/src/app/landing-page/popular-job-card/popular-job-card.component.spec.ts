import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PopularJobCardComponent} from './popular-job-card.component';
import {Job} from "../../models/job.model";
import {TestingModule} from "../../testing.module";

describe('PopularJobCardComponent', () => {
  let component: PopularJobCardComponent;
  let fixture: ComponentFixture<PopularJobCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopularJobCardComponent ],
        imports: [
        TestingModule]
    })
    .compileComponents();
  });

  beforeEach(async () => {
    fixture = TestBed.createComponent(PopularJobCardComponent);
    let fakeJob = new Job();
    component.job=fakeJob;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
