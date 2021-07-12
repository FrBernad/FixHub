import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ContactInfo } from 'src/app/models/contactInfo.model';
import { Injectable } from '@angular/core';

export interface ContactData {
  state: string;
  city: string;
  street: string;
  floor: string;
  departmentNumber: string;
  message: string;
  contactInfoId: string;
  userId: string;
  addressNumber: string;
}

@Injectable({ providedIn: 'root' })
export class ContactService {
  constructor(private http: HttpClient) {}

  getContactInfo(id: number) {
    return this.http.get<ContactInfo[]>(
      environment.apiBaseUrl + '/users/' + id + '/contactInfo'
    );
  }

  newContact(jobId: number, contactData: ContactData) {
    return this.http.post<ContactData>(environment.apiBaseUrl + '/jobs/' + jobId + '/contact', contactData);
  }
}
