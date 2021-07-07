export class User{
  id:number;
  name:string;
  surname:string;
  email:string;
  phoneNumber:string;
  state:string;
  city:string;
  profileImage: number;
  coverImage: number;
  following: User[] ;
  followers: User[];
  providerDetails: {
    location: {
      cities: {id:number,name:string}[],
      state: {id: number, name: string}
    },
    schedule: {
      startTime: Date,
      endTime:Date
    }
  };

}
