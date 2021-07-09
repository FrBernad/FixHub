// export class User {
//   id: number;
//   name: string;
//   surname: string;
//   email: string;
//   phoneNumber: string;
//   state: string;
//   city: string;
//   profileImage: number;
//   coverImage: number;
//   following: User[];
//   followers: User[];
//   providerDetails: {
//     location: {
//       cities: { id: number, name: string }[],
//       state: { id: number, name: string }
//     },
//     schedule: {
//       startTime: Date,
//       endTime: Date
//     }
//   };
//
//   constructor(
//     private _token: string,
//     private _tokenExpirationDate: Date
//   ) {
//   }
// }

export class User {
  following: User[];
  followers: User[];
  providerDetails: {
    location: {
      cities: { id: number, name: string }[],
      state: { id: number, name: string }
    },
    schedule: {
      startTime: Date,
      endTime: Date
    }
  };

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
    public roles: string[]
  ) {
  }

}
