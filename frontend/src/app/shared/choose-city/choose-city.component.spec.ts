import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChooseCityComponent} from './choose-city.component';
import {ProviderDetails, User} from "../../models/user.model";
import {ContactInfo} from "../../models/contact-info.model";
import {State} from "../../discover/discover.service";
import {TestingModule} from "../../testing.module";
import {LoadingSpinnerComponent} from "../loading-spinner/loading-spinner.component" ;


describe('ChooseCityComponent', () => {
  let component: ChooseCityComponent;
  let fixture: ComponentFixture<ChooseCityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChooseCityComponent, LoadingSpinnerComponent],
      imports: [
        TestingModule
      ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseCityComponent);
    const mockProviderDetails: ProviderDetails = {
      location: {
        cities: [{id: 1, name: ''}],
        state: {id: 1, name: ''}
      },
      schedule: {
        startTime: new Date(),
        endTime: new Date()
      },
      jobsCount: 1,
      avgRating: 2,
      reviewCount: 3,
      contactsCount: 4
    };
    const mockContactInfo = new ContactInfo(1, '', '', '', '')
    const mockUser = new User(1, 'name', 'surname', 'email', 'phoneNumber', 'state', 'city', 'profileImage', 'converImage', 2, 1, [mockContactInfo], mockProviderDetails);
    const mockState: State = {id: 1, name: ''};

    component = fixture.componentInstance;
    component.user = mockUser;
    component.isProvider = true;
    component.chosenState = mockState;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
