import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProviderInfoComponent } from './update-provider-info.component';

describe('UpdateProviderInfoComponent', () => {
  let component: UpdateProviderInfoComponent;
  let fixture: ComponentFixture<UpdateProviderInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateProviderInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateProviderInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
