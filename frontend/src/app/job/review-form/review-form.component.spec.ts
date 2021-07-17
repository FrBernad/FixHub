import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ReviewFormComponent} from './review-form.component';
import {TestingModule} from "../../testing.module";
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('ReviewFormComponent', () => {
  let component: ReviewFormComponent;
  let fixture: ComponentFixture<ReviewFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewFormComponent ],
        imports: [
        TestingModule],
      schemas:[NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
