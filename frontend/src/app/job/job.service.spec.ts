import {getTestBed, TestBed} from "@angular/core/testing";
import {JobService} from "./job.service";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HttpStatusCode} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {SingleJob} from "../models/single-job.model";
import {RequestPaginationResult} from "../user/requests/requests.service";
import {User} from "../models/user.model";
import {of} from "rxjs";
import {UserService} from "../auth/services/user.service";

describe('JobService', () => {
  let injector: TestBed;
  let service: JobService;
  let userService: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [JobService, UserService]
    });

    injector = getTestBed();
    service = injector.inject(JobService);
    userService = injector.inject(UserService);
    httpMock = injector.inject(HttpTestingController);
  });

  it('getReviews() should GET and return reviews', () => {
    let requests: RequestPaginationResult = {
      totalPages: 1,
      results: []
    }

    let id = 1;
    service.getReviews({page: 0}, id);
    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs/' + id + '/reviews?page=0');
    expect(req.request.method).toBe('GET');
    req.flush(requests, {
      status: HttpStatusCode.Created,
      statusText: HttpStatusCode.Created.toString(),
      headers: {
        'Link': ["<http://test?page=6>; rel='last'", "<http://test?page=0>; rel='first'"]
      }
    });
  });

  it('createJob() should POST and return created', () => {

    let jobFormData = new FormData();
    jobFormData.append('jobProvided', 'job');
    jobFormData.append('jobCategory', 'MECANICO');
    jobFormData.append('price', '3500');
    jobFormData.append('description', 'description');
    jobFormData.append('paused', 'false');

    service.createJob(jobFormData).subscribe((res) => {
      expect(res.status).toEqual(HttpStatusCode.Created);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs');
    expect(req.request.method).toBe('POST');
    req.flush({}, {status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString()})
  });

  it('getFirstReviews() should GET and return reviews', () => {
    let requests: RequestPaginationResult = {
      totalPages: 1,
      results: []
    }

    let id = 1;

    service.getFirstReviews(id);

    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs/' + id + '/reviews?page=0&pageSize=4');
    expect(req.request.method).toBe('GET');
    req.flush(requests, {
      status: HttpStatusCode.Created,
      statusText: HttpStatusCode.Created.toString(),
      headers: {
        'Link': ['<http://test?page=0>; rel="first"','<http://test?page=6>; rel="last"']
      }
    });

  });

  it('updateJob() should PUT and return created', () => {
    let id = 1;
    let jobFormData = new FormData();
    jobFormData.append('jobProvided', 'job');
    jobFormData.append('jobCategory', 'MECANICO');
    jobFormData.append('price', '3500');
    jobFormData.append('description', 'description');
    jobFormData.append('paused', 'false');

    service.updateJob(id, jobFormData).subscribe((res) => {
      expect(res.status).toEqual(HttpStatusCode.Created);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs/' + id);
    expect(req.request.method).toBe('PUT');
    req.flush({}, {status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString()});
  });

  it('getJob() should GET and return a single job', () => {
    let id = 1;
    let mockUser = new User(1, 'name', 'surname', 'email', 'phoneNumber',
      'state', 'city', 'profileImage', 'coverImage', 2,
      1, null, null);
    let singleJob: SingleJob = {
      id: 1,
      description: "description",
      jobProvided: "job",
      category: "MECANICO",
      price: 3500,
      totalRatings: 3,
      averageRating: 5,
      imagesUrls: [],
      thumbnailImageUrl: "",
      provider: mockUser,
      paused: false,
      canReview: true
    }
    service.getJob(id).subscribe((res) => {
      expect(res).toEqual(singleJob);
    });

    spyOn(userService, 'getUserProviderDetails')
      .and
      .returnValue(of(singleJob.provider.providerDetails));

    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs/' + id);
    expect(req.request.method).toBe('GET');
    req.flush(singleJob, {
      status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString(),
    });
  });


  it('createReview() should POST and return review', () => {
    let id = 1;
    let review = {
      description: "description",
      rating: "5"
    };

    service.createReview(review, id).subscribe((res) => {
      expect(res.status).toEqual(HttpStatusCode.Created);
    });
    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs/' + id + '/reviews');
    expect(req.request.method).toBe('POST');
    req.flush(review, {status: HttpStatusCode.Created, statusText: HttpStatusCode.Created.toString()});
  })

  afterEach(() => {
    httpMock.verify();
  });

});
