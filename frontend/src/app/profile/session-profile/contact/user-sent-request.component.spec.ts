import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSentRequestComponent } from './user-sent-request.component';

describe('ContactComponent', () => {
  let component: UserSentRequestComponent;
  let fixture: ComponentFixture<UserSentRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserSentRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSentRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
