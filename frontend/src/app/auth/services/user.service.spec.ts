import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {ProviderInfo, UserService} from "./user.service";
import {HttpStatusCode} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {User} from "../../models/user.model";
import {RouterTestingModule} from "@angular/router/testing";
import {BehaviorSubject} from "rxjs";

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
    ['', ''],
    1,
    2,
    [null],
    null,
    true,
    false
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
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/following/' + mockUser.id,);
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.NoContent, statusText: HttpStatusCode.NoContent.toString()})
  });

  it('unfollow() should return no content', () => {
    service.unfollow(mockUser.id).subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/following/' + mockUser.id);
    expect(req.request.method).toBe('DELETE');
    req.flush({}, {status: HttpStatusCode.NoContent, statusText: HttpStatusCode.NoContent.toString()})
  });


  it('updateProfileInfo() should', () => {
    let mockBSuser = new BehaviorSubject(mockUser);

    service.updateProfileInfo("aaaa");
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user');
    expect(req.request.method).toBe('PUT');
    req.flush({}, {
      status: HttpStatusCode.Ok,
      statusText: HttpStatusCode.Created.toString()
    })
  });

  it('updateCoverImage()', () => {
    const mockCoverImage = new FormData();
    service.updateCoverImage(mockCoverImage).subscribe((res)=>{
      expect(res).toEqual(mockCoverImage);
    });
    const req = httpMock.expectOne(environment.apiBaseUrl+'/user/coverImage');
    expect(req.request.method).toBe('PUT');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});

  });

  it('updateProviderInfo()', () => {
    service.updateProviderInfo(mockProviderInfo);
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/account/provider');
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()})
  });

  it('updateProfileImage', function () {
    const mockProfileImage = new FormData();
    service.updateProfileImage(mockProfileImage).subscribe((res)=>{
      expect(res).toEqual(mockProfileImage);
    });
    const req = httpMock.expectOne(environment.apiBaseUrl+'/user/coverImage');
    expect(req.request.method).toBe('PUT');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});
  });


});
