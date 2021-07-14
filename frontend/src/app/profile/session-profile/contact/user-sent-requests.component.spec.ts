import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UserSentRequestsComponent} from './user-sent-requests.component';

describe('ContactComponent', () => {
  let component: UserSentRequestsComponent;
  let fixture: ComponentFixture<UserSentRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserSentRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSentRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
