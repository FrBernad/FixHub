export class ContactInfo {
  id: number;
  addressNumber: string;
  city: string;
  departmentNumber: string;
  floor: string;
  state: string;
  street: string;

  constructor(id: number, addressNumber: string, city: string, departmentNumber: string, floor: string, state: string, street: string) {
    this.id = id;
    this.addressNumber = addressNumber;
    this.city = city;
    this.departmentNumber = departmentNumber;
    this.floor = floor;
    this.state = state;
    this.street = street;
  }
}
