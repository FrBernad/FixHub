import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {environment} from "../../../environments/environment";
import {HttpHeaders, HttpStatusCode} from "@angular/common/http";
import {JobRequest} from "../../models/job-request.model";
import {UserService} from "../../auth/services/user.service";
import {ContactData, RequestPaginationResult, RequestsService} from "./requests.service";
import {of} from "rxjs";
import {RouterTestingModule} from "@angular/router/testing";

describe('RequestsService', () => {
  let injector: TestBed;
  let requestsService: RequestsService;
  let userService: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,RouterTestingModule],
      providers: [RequestsService, UserService],
    });

    injector = getTestBed();
    requestsService = injector.inject(RequestsService);
    userService = injector.inject(UserService);
    httpMock = injector.inject(HttpTestingController);
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1});
  });


  it('newContact() should return created location', () => {
    let contactData: ContactData = {
      state: "state",
      city: "city",
      street: "street",
      floor: "floor",
      departmentNumber: "5",
      message: "message",
      contactInfoId: "1",
      userId: "userId",
      addressNumber: "5"
    }

    let jobId = 1;
    let resultId = 2;

    requestsService.newContact(jobId, contactData).subscribe((res) => {
      expect(res).toEqual("2");
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs/' + jobId + '/contact');
    spyOn(userService, 'getUserContactInfo').and.returnValue(of(undefined));
    expect(req.request.method).toBe('POST');

    req.flush(contactData,
      {
        headers: new HttpHeaders({Location: "user/jobs/requests/" + resultId}),
        status: HttpStatusCode.Created,
        statusText: HttpStatusCode.Created.toString()
      });

  });

  it('getProviderRequests() should return requests', () => {
    let requests: RequestPaginationResult = {
      // page: 0,
      totalPages: 1,
      results: []
    }

    requestsService.getProviderRequests({page: 0});

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + userService.user.getValue().id + '/requests/received?page=0');
    expect(req.request.method).toBe('GET');
    req.flush(requests, {status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString()});
  });

  it(' getJobRequest() should return request', () => {
    let request: JobRequest = {
      id: 1,
      jobProvided: "jobProvided",
      jobId: 2,
      message: "message",
      status: "status",
      provider: undefined,
      user: undefined,
      date: new Date,
      contactInfo: undefined
    }

    let requestId = 1;

    requestsService.getSentJobRequest(requestId).subscribe((res) => {
      expect(res).toEqual(request);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + userService.user.getValue().id + '/requests/sent/' + request.id);
    expect(req.request.method).toBe('GET');
    req.flush(request);
  });

  it('getUserSentRequests() should return request', () => {
    let requests: RequestPaginationResult = {
      // page: 0,
      totalPages: 1,
      results: []
    }
    requestsService.getUserSentRequests({page: 0});

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + userService.user.getValue().id + '/requests/sent?page=0');
    expect(req.request.method).toBe('GET');
    req.flush(requests);
  });


  it('changeContactStatus() should return created', () => {
    requestsService.changeReceivedRequestStatus(1, "FINISHED")
      .subscribe((res) => {
        expect(res.status).toEqual(HttpStatusCode.Created);
      });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + userService.user.getValue().id + '/requests/received/' + 1);
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString()});
  });

  afterEach(() => {
    httpMock.verify();
  });

});
