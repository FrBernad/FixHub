import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {ProfileInfo, ProviderInfo, UserService} from "./user.service";
import {HttpStatusCode} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {User} from "../../models/user.model";
import {RouterTestingModule} from "@angular/router/testing";
import {of} from "rxjs";

describe('FollowService', () => {

  let injector: TestBed;
  let service: UserService;
  let httpMock: HttpTestingController;
  let mockUser = new User(
    1,
    'name',
    'surname',
    'email',
    'phoneNumber',
    'state',
    'city',
    'profileImage',
    'converImage',
    2,
    1,
    [null],
    null,
  );

  const mockProviderInfo: ProviderInfo = {
    schedule: {
      startTime: null,
      endTime: null
    },
    location: {
      cities: [{id: 1, name: ''}],
      state: {id: 1, name: ''}
    }
  }
  const mockImage = new FormData();


  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [
        UserService
      ],
    });

    injector = getTestBed();
    service = injector.inject(UserService);
    httpMock = injector.inject(HttpTestingController);
  });

  it('getUser() should return user', () => {
    service.getUser(mockUser.id).subscribe((res) => {
      expect(res).toEqual(mockUser);
    });
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + mockUser.id);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser, {status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString()});
  });

  it('populateUserData() should return user', () => {
    service.populateUserData().subscribe((res) => {
      expect(res).toEqual(mockUser);
    });
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser, {status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString()});
  });

  it('follow() should return no content', () => {
    service.follow(mockUser.id).subscribe();
    spyOn(service, 'populateUserData').and.returnValue(of(undefined));
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/following/' + mockUser.id,);
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.NoContent, statusText: HttpStatusCode.NoContent.toString()})
  });

  it('unfollow should return no content', () => {
    service.unfollow(mockUser.id).subscribe();
    spyOn(service, 'populateUserData').and.returnValue(of(undefined));
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/following/' + mockUser.id);
    expect(req.request.method).toBe('DELETE');
    req.flush({}, {status: HttpStatusCode.NoContent, statusText: HttpStatusCode.NoContent.toString()})
  });


  it('updateProfileInfo should return OK', () => {
    let mockProfileInfo: ProfileInfo = {
      name: '',
      surname: '',
      phoneNumber: '',
      state: '',
      city: ''
    }

    service.updateProfileInfo(mockProfileInfo).subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user');
    expect(req.request.method).toBe('PUT');
    req.flush({}, {
      status: HttpStatusCode.Ok,
      statusText: HttpStatusCode.Created.toString()
    })
  });

  it('updateCoverImage should return OK', () => {
    service.updateCoverImage(mockImage).subscribe((res) => {
      expect(res).toEqual(mockImage);
    });
    spyOn(service, 'populateUserData').and.returnValue(of(undefined));
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/coverImage');
    expect(req.request.method).toBe('PUT');
    req.flush(mockImage, {status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});

  });

  it('updateProviderInfo should return OK', () => {
    service.updateProviderInfo(mockProviderInfo).subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/account/provider');
    spyOn(service, 'populateUserData').and.returnValue(of(undefined));
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()})
  });

  it('updateProfileImage should return OK', function () {
    service.updateProfileImage(mockImage).subscribe((res) => {
      expect(res).toEqual(mockImage);
    });
    spyOn(service, 'populateUserData').and.returnValue(of(undefined));
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/profileImage');
    expect(req.request.method).toBe('PUT');
    req.flush(mockImage, {status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});
  });

  afterEach(() => {
    httpMock.verify();
  });

});
