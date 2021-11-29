import {getTestBed, TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {WorksService} from "./works.service";
import {environment} from "../../../../../environments/environment";
import {HttpStatusCode} from "@angular/common/http";
import {RouterTestingModule} from "@angular/router/testing";
import {UserService} from "../../../../auth/services/user.service";

describe('WorksService', () => {
  let injector: TestBed;
  let service: WorksService;
  let userService: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [WorksService, UserService],
    });

    injector = getTestBed();
    userService = injector.inject(UserService);
    service = injector.inject(WorksService);
    httpMock = injector.inject(HttpTestingController);

    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1});
  });

  it('getUserJobs(jp: JobPaginationQuery) should return user jobs', () => {
    service.getUserJobs({page:0});

    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/' + userService.user.getValue().id + '/jobs?page=0');
    expect(req.request.method).toBe('GET');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});
  });


});

