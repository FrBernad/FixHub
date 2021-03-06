import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {FollowPaginationResult, FollowService} from "./follow.service";
import {environment} from "../../../environments/environment";
import {HttpStatusCode} from "@angular/common/http";
import {RouterTestingModule} from "@angular/router/testing";

describe('FollowService', () => {

  let injector: TestBed;
  let service: FollowService;
  let httpMock: HttpTestingController;
  let result: FollowPaginationResult = {
    totalPages: 4,
    results: [null]
  };
  let fpq = {
    page: 2,
    pageSize: 4
  }
  let id = 2;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [
        FollowService
      ],
    });

    injector = getTestBed();
    service = injector.inject(FollowService);
    httpMock = injector.inject(HttpTestingController);
  });

  it('getFollowers() should return data', () => {
    service.getFollowers(fpq, id);
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + id + '/followers?page=' + fpq.page + '&pageSize=' + fpq.pageSize);
    expect(req.request.method).toBe('GET');
    req.flush(result, {
      status: HttpStatusCode.Created,
      statusText: HttpStatusCode.Created.toString(),
      headers: {
        'Link': "<http://test?page=6>; rel='last', <http://test?page=0>; rel='first'"
      }
    });
  });

  it('getFollowing() should return data', () => {
    service.getFollowing(fpq, id);
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + id + '/following?page=' + fpq.page + '&pageSize=' + fpq.pageSize);
    expect(req.request.method).toBe('GET');
    req.flush(result, {
      status: HttpStatusCode.Created,
      statusText: HttpStatusCode.Created.toString(),
      headers: {
        Link: ['<http://test?page=0>; rel="first"', '<http://test?page=0>; rel="last"']
      }
    });

  });

  afterEach(() => {
    httpMock.verify();
  });

});
