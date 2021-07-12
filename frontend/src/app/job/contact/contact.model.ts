import {JobStatusModel} from "../../models/job-status.model";

export class Contact {
  public state: string;
  public city: string;
  public street: string;
  public addressNumber: number;
  public floor: number;
  public departmentNumber: string;
  public message: string;
  public date: Date;
  public status: JobStatusModel

  constructor(state: string, city: string, street: string, addressNumber: number, floor: number, departmentNumber: string, message: string, date:Date) {
    this.state = state;
    this.city = city;
    this.street = street;
    this.addressNumber = addressNumber;
    this.floor = floor;
    this.departmentNumber = departmentNumber;
    this.message = message;
    this.date= date;
    this.status=JobStatusModel.PENDING;
  }

  isWorkInProgress():boolean{
    return this.status==JobStatusModel.IN_PROGRESS;
  }

  isWorkPending():boolean{
    return this.status==JobStatusModel.PENDING;
  }


}
