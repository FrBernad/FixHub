export interface ProviderDetails{
  location: {
    cities: { id: number, name: string }[],
    state: { id: number, name: string }
  },
  schedule: {
    startTime: Date,
    endTime: Date
  }
}

export class User {
  following: User[];
  followers: User[];

  constructor(
    public id: number,
    public name: string,
    public surname: string,
    public email: string,
    public phoneNumber: string,
    public state: string,
    public city: string,
    public profileImage: string,
    public coverImage: string,
    public roles: string[],
    public providerDetails?: ProviderDetails
  ) {
  }

}
