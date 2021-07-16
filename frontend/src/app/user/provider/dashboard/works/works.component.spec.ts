import {ComponentFixture, TestBed} from '@angular/core/testing';

import {WorksComponent} from './works.component';
import {TestingModule} from "../../../../testing.module";

describe('WorksComponent', () => {
  let component: WorksComponent;
  let fixture: ComponentFixture<WorksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorksComponent ],
        imports: [
        TestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
