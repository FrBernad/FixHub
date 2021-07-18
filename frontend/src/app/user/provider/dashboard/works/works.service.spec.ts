import {getTestBed, TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {WorksService} from "./works.service";
import {environment} from "../../../../../environments/environment";
import {HttpStatusCode} from "@angular/common/http";

describe('WorksService', () => {
  let injector: TestBed;
  let service: WorksService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [WorksService],
    });

    injector = getTestBed();
    service = injector.inject(WorksService);
    httpMock = injector.inject(HttpTestingController);
  });

  it('getUserJobs(jp: JobPaginationQuery) should return user jobs', () => {
    service.getUserJobs({page:0});

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/jobs?page=0');
    expect(req.request.method).toBe('GET');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});
  });


});

