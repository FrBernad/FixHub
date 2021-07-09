import {HttpClient} from "@angular/common/http";
import {Job} from "../models/job.model";
import {environment} from "../../environments/environment";
import {Injectable} from "@angular/core";

@Injectable({providedIn: 'root'})
export class JobService {

  constructor(private http: HttpClient) {
  }

  createJob(jobData: {
              jobProvided: string,
              jobCategory: string,
              price: number,
              description: string,
              images: File[]
            }){
    console.log(jobData);

    return this.http.post<{jobProvided: string,
      jobCategory: string,
      price: number,
      description: string,
      images: File[]}>(environment.apiBaseUrl+'/jobs/new',jobData);

  }


  updateJob(job: Job, JobData: {}) {


  }

  getJob(id: number) {
    return this.http.get<Job>(environment.apiBaseUrl + '/jobs/' + id);
  }


}
