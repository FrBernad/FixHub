import {Job} from "./job.model";
import {User} from "./user.model";

export class SingleJob extends Job{
    canReview:boolean;


  constructor(id: number, description: string, jobProvided: string, category: string, price: number, totalRatings: number, averageRating: number, images: [], thumbnailImage: string, provider: User, paused: boolean, canReview: boolean) {
    super(id, description, jobProvided, category, price, totalRatings, averageRating, images, thumbnailImage, provider, paused);
    this.canReview = canReview;
  }
}
