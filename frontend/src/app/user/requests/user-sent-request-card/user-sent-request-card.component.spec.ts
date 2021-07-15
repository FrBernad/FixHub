import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UserSentRequestCardComponent} from './user-sent-request-card.component';

describe('UserSentRequestCardComponent', () => {
  let component: UserSentRequestCardComponent;
  let fixture: ComponentFixture<UserSentRequestCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserSentRequestCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSentRequestCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
