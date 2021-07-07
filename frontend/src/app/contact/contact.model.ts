export class Contact {
  public state: string;
  public city: string;
  public street: string;
  public addressNumber: number;
  public floor: number;
  public departmentNumber: string;
  public message: string;

  constructor(state: string, city: string, street: string, addressNumber: number, floor: number, departmentNumber: string, message: string) {
    this.state = state;
    this.city = city;
    this.street = street;
    this.addressNumber = addressNumber;
    this.floor = floor;
    this.departmentNumber = departmentNumber;
    this.message = message;
  }
}
