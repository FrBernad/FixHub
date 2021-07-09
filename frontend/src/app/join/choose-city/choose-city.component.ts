import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-choose-city',
  templateUrl: './choose-city.component.html',
  styleUrls: ['./choose-city.component.scss']
})
export class ChooseCityComponent implements OnInit {

  cities = [{id: '1', name: 'Palermo'}, {id: 2, name: 'Belgrano'}, {id: 3, name: 'Caballito'}];
  chooseCityForm: FormGroup;
  citySelected: { id: number, name: string } = {id: -1, name: ''};
  citiesSelected = new FormArray([], Validators.required);
  @Output() citiesChosen = new EventEmitter<void>();


  constructor() {
  }

  ngOnInit(): void {
    this.chooseCityForm = new FormGroup({
      cities: this.citiesSelected
    });
  }

  selectCity(city: { id: number, name: string }) {
    this.citySelected = city;
    for (let i = 0; i < this.citiesSelected.length; i++) {
      if (city.id === this.citiesSelected[i].id)
        return;
    }
    (<FormArray>this.chooseCityForm.get('cities')).push(
      new FormControl(city)
    );
  }

  deleteCity(index: number) {
    this.citiesSelected.removeAt(index);
    let len = this.citiesSelected.length;
    if (len == 0) {
      this.citySelected = {id: -1, name: ''};
    } else {
      this.citySelected = this.citiesSelected[this.citiesSelected.length - 1];
    }
  }

  onSubmit() {
    if (!this.chooseCityForm.valid) {
      this.chooseCityForm.markAllAsTouched();
      return;
    }
    this.citiesChosen.emit();
  }
}
