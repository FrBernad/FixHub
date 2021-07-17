import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PopularJobCardComponent} from './popular-job-card.component';
import {TestingModule} from "../../testing.module";
import {Job} from "../../models/job.model";

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
    component.job=new Job();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
