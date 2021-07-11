import {User} from "../models/user.model";

export class Review {
  constructor(
    public id: number,
    public description: string,
    public rating: number,
    public creationDate: Date,
    public reviewer: User
  ) {
  }
}
