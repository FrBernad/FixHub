import {User} from "./user.model";

export class Job {
  id: number;
  description: string;
  jobProvided: string;
  category: string;
  price: number;
  totalRatings: number;
  averageRating: number;
  imagesUrls: [];
  thumbnailImageUrl: string;
  provider: User;
  paused: boolean;

  constructor(id: number, description: string, jobProvided: string, category: string, price: number, totalRatings: number, averageRating: number, images: [], thumbnailImage: string, provider: User, paused: boolean) {
    this.id = id;
    this.description = description;
    this.jobProvided = jobProvided;
    this.category = category;
    this.price = price;
    this.totalRatings = totalRatings;
    this.averageRating = averageRating;
    this.imagesUrls = images;
    this.thumbnailImageUrl = thumbnailImage;
    this.provider = provider;
    this.paused = paused;
  }
}
