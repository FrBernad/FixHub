import {environment} from "../../../environments/environment";
import {HttpStatusCode} from "@angular/common/http";
import {NotificationsService} from "./notifications.service";
import {getTestBed, TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {UserService} from "../../auth/services/user.service";


describe('NotificationsService', () => {
  let injector: TestBed;
  let service: NotificationsService;
  let userService: UserService;
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
    userService = injector.inject(UserService);
    userService.user.next({...userService.user.getValue(), roles: ['VERIFIED'], id: 1})
  });

  it('refreshNotifications() should get number of unseen notifications', () => {
    service.refreshNotifications();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/'+userService.user.getValue().id+'/notifications/unseen');
    expect(req.request.method).toBe('GET');
  });


  it('getNotifications() should get notifications', () => {
    service.getNotifications({page:0});
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/'+userService.user.getValue().id +'/notifications?page=0');
    expect(req.request.method).toBe('GET');
  });


  it('markAsReadNotification() should mark as read the notification', () => {
    let id = 1;
    service.markAsReadNotification(id).subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/'+userService.user.getValue().id+'/notifications/' + id );
    expect(req.request.method).toBe('PUT');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});

  });


  it('markAsReadAllNotification() should mark as read all notification', () => {
    service.markAsReadAllNotifications().subscribe();
    const req = httpMock.expectOne(environment.apiBaseUrl + '/users/'+userService.user.getValue().id+'/notifications');
    expect(req.request.method).toBe('PUT');
    req.flush({},{status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});

  });

  afterEach(() => {
    httpMock.verify();
  });

});
