import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LoadingScreenComponent} from './loading-screen.component';
import {TestingModule} from "../testing.module";

describe('LoadingScreenComponent', () => {
  let component: LoadingScreenComponent;
  let fixture: ComponentFixture<LoadingScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadingScreenComponent],
      imports: [
       TestingModule
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
