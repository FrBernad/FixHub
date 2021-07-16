import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceivedRequestCardComponent } from './received-request-card.component';

describe('ReceivedRequestCardComponent', () => {
  let component: ReceivedRequestCardComponent;
  let fixture: ComponentFixture<ReceivedRequestCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReceivedRequestCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceivedRequestCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
