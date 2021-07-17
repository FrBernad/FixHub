import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";

@Injectable({providedIn: 'root'})
export class SessionProfileService {

  constructor(
    private http:HttpClient
  ) {
  }

  updateProfileImage(profileImage: FormData){
    return this.http.put<FormData>(environment.apiBaseUrl + '/user/profileImage', profileImage, {observe: 'response'});
  }

  updateCoverImage(coverImage: FormData) {
    return this.http.put<FormData>(environment.apiBaseUrl + '/user/coverImage', coverImage, {observe: "response"});
  }
}
