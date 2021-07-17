import {NotificationType} from "./notification-type-enum.model";

export class Notification{
    id:number;
    date:Date;
    resource:number;
    seen:boolean;
    type:NotificationType;

  constructor(id: number, date: Date, resource: number, seen: boolean, type: NotificationType) {
    this.id = id;
    this.date = date;
    this.resource = resource;
    this.seen = seen;
    this.type = type;
  }
}

