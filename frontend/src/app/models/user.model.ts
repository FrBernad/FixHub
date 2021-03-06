import {ContactInfo} from "./contact-info.model";

export interface ProviderDetails {
  location: {
    cities: { id: number, name: string }[],
    state: { id: number, name: string }
  },
  schedule: {
    startTime: Date,
    endTime: Date
  },
  jobsCount: number,
  avgRating: number;
  reviewCount: number;
  contactsCount: number;
}

export class User {
  public roles: string[];

  constructor(
    public id: number,
    public name: string,
    public surname: string,
    public email: string,
    public phoneNumber: string,
    public state: string,
    public city: string,
    public profileImageUrl: string,
    public coverImageUrl: string,
    public followingCount: number,
    public followersCount: number,
    public contactInfo?: ContactInfo[],
    public providerDetails?: ProviderDetails,
  ) {
  }

}
