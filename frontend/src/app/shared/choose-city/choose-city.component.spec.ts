import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChooseCityComponent} from './choose-city.component';
import {ProviderDetails, User} from "../../models/user.model";
import {ContactInfo} from "../../models/contact-info.model";
import {State} from "../../discover/discover.service";
import {TestingModule} from "../../testing.module";

describe('ChooseCityComponent', () => {
  let component: ChooseCityComponent;
  let fixture: ComponentFixture<ChooseCityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChooseCityComponent],
      imports: [
        TestingModule
      ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseCityComponent);
    const fakeProviderDetails: ProviderDetails={
      location:{
        cities:[{id:1,name:''}],
        state:{id:1,name:''}
      },
      schedule:{
        startTime: new Date(),
        endTime: new Date()
      },
      jobsCount:1,
      avgRating:2,
      reviewCount:3,
      contactsCount:4
    };

    let fakeContactInfo = new ContactInfo(
        1,
        '',
        '',
        '',
        ''
    )

    let fakeUser = new User(
      1,
      'name',
      'surname',
      'email',
      'phoneNumber',
      'state',
      'city',
      'profileImage',
      'converImage',
      ['',''],
      1,
      2,
      [fakeContactInfo],
      fakeProviderDetails,
      true,
      false
    );

    const fakeState: State={
      id:1,
      name:''
    };

    component = fixture.componentInstance;
    component.user = fakeUser;
    component.isProvider = true;
    component.chosenState = fakeState;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
