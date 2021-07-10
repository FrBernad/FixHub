import {User} from "./user.model";
import {JobCategoryModel} from "./jobCategory.model";

export class Job{
  id:number;
  description: string;
  jobProvided: string;
  category: JobCategoryModel;
  price: number;
  totalRatings: number;
  averageRating: number;
  images :  [];
  thumbnailId: number;
  reviews : [];
  provider: User;
  paused: boolean;
}
