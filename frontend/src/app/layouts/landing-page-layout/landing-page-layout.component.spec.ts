import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LandingPageLayoutComponent} from './landing-page-layout.component';
import {TestingModule} from "../../testing.module";

describe('LandingPageLayoutComponent', () => {
  let component: LandingPageLayoutComponent;
  let fixture: ComponentFixture<LandingPageLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LandingPageLayoutComponent],
      imports: [
        TestingModule
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LandingPageLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
