import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecoverEmailPopupComponent } from './recover-email-popup.component';

describe('RecoverEmailPopupComponent', () => {
  let component: RecoverEmailPopupComponent;
  let fixture: ComponentFixture<RecoverEmailPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecoverEmailPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecoverEmailPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
