import {User} from "./user.model";

export class Job{
  id: number;
  description: string;
  jobProvided: string;
  category: string;
  price: number;
  totalRatings: number;
  averageRating: number;
  images: [];
  thumbnailImage: string;
  provider: User;
  paused: boolean;
}
