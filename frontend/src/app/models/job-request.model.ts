import {User} from "./user.model";
import {ContactInfo} from "./contact-info.model";

export class JobRequest {
  id: number;
  jobProvided: string;
  jobId: number;
  message: string;
  status: string;
  provider: User;
  user: User;
  date: Date;
  contactInfo: ContactInfo;
}
