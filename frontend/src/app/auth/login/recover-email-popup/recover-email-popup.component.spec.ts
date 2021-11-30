import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RecoverEmailPopupComponent} from './recover-email-popup.component';
import {AuthService} from "../../services/auth.service";
import {TestingModule} from "../../../testing.module";
import {ReactiveFormsModule} from "@angular/forms";

describe('RecoverEmailPopupComponent', () => {
  let component: RecoverEmailPopupComponent;
  let fixture: ComponentFixture<RecoverEmailPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RecoverEmailPopupComponent],
      imports: [
       TestingModule,ReactiveFormsModule
      ],
      providers:[
        AuthService
      ]
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
