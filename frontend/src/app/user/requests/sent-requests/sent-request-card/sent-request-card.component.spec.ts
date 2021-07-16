import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SentRequestCard} from './sent-request-card.component';

describe('UserSentRequestCardComponent', () => {
  let component: SentRequestCard;
  let fixture: ComponentFixture<SentRequestCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SentRequestCard ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SentRequestCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
