import {User} from "./User";

export class Job{
  id:number;
  description: string;
  jobProvided: string;
  category: string;
  price: number;
  totalRatings: number;
  averageRating: number;
  images :  [];
  reviews : [];
  provider: User;
  paused: boolean;

}
