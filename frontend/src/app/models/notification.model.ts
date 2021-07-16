import {NotificationType} from "./notification-type-enum.model";

export class Notification{
    id:number;
    date:Date;
    resource:number;
    seen:boolean;
    type:NotificationType;
}

