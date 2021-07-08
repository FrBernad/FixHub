import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopularJobCardComponent } from './popular-job-card.component';

describe('PopularJobCardComponent', () => {
  let component: PopularJobCardComponent;
  let fixture: ComponentFixture<PopularJobCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopularJobCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PopularJobCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
