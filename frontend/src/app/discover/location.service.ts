import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';
import {throwError, BehaviorSubject, Subject} from 'rxjs';
import {environment} from '../../environments/environment';
import {Job} from "../models/job.model";

@Injectable({providedIn: 'root'})
export class LocationService {

  states = new Subject<string>();
  categories = new Subject<string>();

  constructor(
    private http: HttpClient,
  ) {
  }


}
