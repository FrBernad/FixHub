import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ContactInfo } from 'src/app/models/contactInfo.model';
import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class ContactService {

  constructor(private http: HttpClient) {
  }

  getContactInfo(id: number) {
    return this.http.get<ContactInfo[]>(environment.apiBaseUrl+ '/users/' + id + '/contactInfo');
  }
}
