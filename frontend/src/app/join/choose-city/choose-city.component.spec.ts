import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseCityComponent } from './choose-city.component';

describe('ChooseCityComponent', () => {
  let component: ChooseCityComponent;
  let fixture: ComponentFixture<ChooseCityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChooseCityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseCityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
