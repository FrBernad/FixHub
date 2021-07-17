import {getTestBed, TestBed} from "@angular/core/testing";
import {SessionProfileService} from "./session-profile.service";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HttpStatusCode} from "@angular/common/http";
import {environment} from "../../../../environments/environment";

describe('JobService', () => {
  let injector: TestBed;
  let service: SessionProfileService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionProfileService]
    });

    injector = getTestBed();
    service = injector.inject(SessionProfileService);
    httpMock = injector.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('updateProfileImage() should PUT and return ok', () => {
    service.updateProfileImage(null).subscribe((res)=> {
      expect(res.status).toEqual(HttpStatusCode.Ok);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/profileImage');
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()})
  });

  it('updateCoverImage() should PUT and return ok', () => {
    service.updateCoverImage(null).subscribe((res)=> {
      expect(res.status).toEqual(HttpStatusCode.Ok);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/coverImage');
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()})
  });

});
