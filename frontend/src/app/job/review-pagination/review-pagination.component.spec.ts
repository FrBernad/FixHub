import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewPaginationComponent} from './review-pagination.component';
import {TestingModule} from "../../testing.module";
import {Job} from "../../models/job.model";

describe('ReviewPaginationComponent', () => {
  let component: ReviewPaginationComponent;
  let fixture: ComponentFixture<ReviewPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewPaginationComponent ],
       imports: [
        TestingModule],
      providers:[]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewPaginationComponent);
    component.job=new Job();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
