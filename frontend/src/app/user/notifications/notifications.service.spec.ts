import {environment} from "../../../environments/environment";
import {HttpStatusCode} from "@angular/common/http";
import {NotificationsService} from "./notifications.service";
import {getTestBed, TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";


describe('NotificationsService', () => {

  let injector: TestBed;
  let service: NotificationsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,
        RouterTestingModule],
      providers: [NotificationsService],
    });

    injector = getTestBed();
    service = injector.inject(NotificationsService);
    httpMock = injector.inject(HttpTestingController);
  });

  it('refreshNotifications() should get number of unseen notifications', () => {
    service.refreshNotifications();

    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/unseenNotifications');
    expect(req.request.method).toBe('GET');
  });


  it('getNotifications() should get notifications', () => {
    service.getNotifications({page:0});
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/notifications?page=0');
    expect(req.request.method).toBe('GET');
  });


  it('markAsReadNotification() should mark as read the notification', () => {
    let id = 1;
    service.markAsReadNotification(id).subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/notifications/' + id );
    expect(req.request.method).toBe('PUT');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});

    const req2 = httpMock.expectOne(environment.apiBaseUrl + '/user/unseenNotifications');
    expect(req2.request.method).toBe('GET');
  });


  it('markAsReadAllNotification() should mark as read all notification', () => {
    service.markAsReadAllNotifications().subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/user/notifications' );
    expect(req.request.method).toBe('PUT');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});

    const req2 = httpMock.expectOne(environment.apiBaseUrl + '/user/unseenNotifications');
    expect(req2.request.method).toBe('GET');
  });

  afterEach(() => {
    httpMock.verify();
  });

});
