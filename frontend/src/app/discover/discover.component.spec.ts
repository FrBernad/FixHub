import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DiscoverComponent} from './discover.component';
import {LoadingSpinnerComponent} from "../shared/loading-spinner/loading-spinner.component";
import {TestingModule} from "../testing.module";

describe('DiscoverComponent', () => {
  let component: DiscoverComponent;
  let fixture: ComponentFixture<DiscoverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiscoverComponent,LoadingSpinnerComponent],
        imports: [
        TestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DiscoverComponent);
    component = fixture.componentInstance;
    window.history.pushState({},'category');
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
