import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestJobCardComponent } from './request-job-card.component';

describe('RequestJobCardComponent', () => {
  let component: RequestJobCardComponent;
  let fixture: ComponentFixture<RequestJobCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestJobCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestJobCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
