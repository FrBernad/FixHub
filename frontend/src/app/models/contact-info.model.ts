export class ContactInfo {
  constructor(public id: number,
              public addressNumber: string,
              public city: string,
              public state: string,
              public street: string,
              public floor?: string,
              public departmentNumber?: string) {
  }
}
