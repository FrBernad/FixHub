import {JobStatusModel} from "../../models/job-status.model";
import {User} from "../../models/user.model";
import {Job} from "../../models/job.model";
import {ContactInfo} from "../../models/contactInfo.model";

export class Contact {

  private _id:number;
  private _user:User;
  private _provider:User;
  private _message: string;
  private _status: JobStatusModel;
  private _date: Date;
  private _job:Job;
  private _contactInfo:ContactInfo;


  constructor(id: number, user: User, provider: User, message: string, status: JobStatusModel, date: Date, job: Job, contactInfo: ContactInfo) {
    this._id = id;
    this._user = user;
    this._provider = provider;
    this._message = message;
    this._status = status;
    this._date = date;
    this._job = job;
    this._contactInfo = contactInfo;
  }

  isWorkInProgress():boolean{
    return this._status==JobStatusModel.IN_PROGRESS;
  }

  isWorkPending():boolean{
    return this._status==JobStatusModel.PENDING;
  }


  get id(): number {
    return this._id;
  }

  get user(): User {
    return this._user;
  }

  get provider(): User {
    return this._provider;
  }

  get message(): string {
    return this._message;
  }

  get status(): JobStatusModel {
    return this._status;
  }

  get date(): Date {
    return this._date;
  }

  get job(): Job {
    return this._job;
  }

  get contactInfo(): ContactInfo {
    return this._contactInfo;
  }
}
