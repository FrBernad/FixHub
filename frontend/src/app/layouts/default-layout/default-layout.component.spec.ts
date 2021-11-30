import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DefaultLayoutComponent} from './default-layout.component';
import {TestingModule} from "../../testing.module";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";

describe('DefaultLayoutComponent', () => {
  let component: DefaultLayoutComponent;
  let fixture: ComponentFixture<DefaultLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefaultLayoutComponent ],
      schemas:[CUSTOM_ELEMENTS_SCHEMA],
        imports: [
        TestingModule,]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DefaultLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
