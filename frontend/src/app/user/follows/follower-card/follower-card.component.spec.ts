import {ComponentFixture, TestBed} from '@angular/core/testing';

import {FollowerCardComponent} from './follower-card.component';
import {ProviderDetails, User} from "../../../models/user.model";
import {ContactInfo} from "../../../models/contact-info.model";
import {TestingModule} from "../../../testing.module";

describe('FollowerCardComponent', () => {
  let component: FollowerCardComponent;
  let fixture: ComponentFixture<FollowerCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FollowerCardComponent ],
        imports: [
        TestingModule]
    })
    .compileComponents();
  });

  beforeEach(async () => {
    fixture = TestBed.createComponent(FollowerCardComponent);
    component = fixture.componentInstance;

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
    component.follower=fakeUser;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
