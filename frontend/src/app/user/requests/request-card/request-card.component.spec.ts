import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RequestCardComponent} from './request-card.component';
import {JobRequest} from "../../../models/job-request.model";
import {TestingModule} from "../../../testing.module";

describe('RequestCardComponent', () => {
  let component: RequestCardComponent;
  let fixture: ComponentFixture<RequestCardComponent>;
  let mockType = 'Type';
  let mockJobRequest = new JobRequest();

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestCardComponent ],
      imports:[
        TestingModule
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestCardComponent);
    component = fixture.componentInstance;
    component.type=mockType;
    component.request=mockJobRequest;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
