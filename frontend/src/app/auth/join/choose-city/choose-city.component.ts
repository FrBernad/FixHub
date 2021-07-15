import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from "../../../models/user.model";
import {City, JobsService, State} from "../../../discover/jobs.service";

@Component({
  selector: 'app-choose-city',
  templateUrl: './choose-city.component.html',
  styleUrls: ['./choose-city.component.scss'],
})
export class ChooseCityComponent implements OnInit {

  @Input() user: User;
  @Input() chosenState: State;
  @Input() isProvider: boolean;
  @Output() citiesChosen = new EventEmitter<City[]>();

  cities: City[];
  isFetching = true;
  chooseCityForm: FormGroup;
  citySelected: { id: number; name: string } = {id: -1, name: ''};

  citiesSelected: FormArray = new FormArray([], Validators.required);

  constructor(
    private jobsService: JobsService
  ) {

  }

  ngOnInit(): void {
    this.chooseCityForm = new FormGroup({
      cities: this.citiesSelected,
    });


    if (this.isProvider != null && this.isProvider) {
      this.user.providerDetails.location.cities.forEach((city) => {
        (<FormArray>this.chooseCityForm.get('cities')).push(
          new FormControl(city)
        );
      });
    }

    this.jobsService.getStateCities(this.chosenState.id.toString()).subscribe(
      (cities) => {
        this.cities = cities;
        this.isFetching = false;

      });

  }

  selectCity(city: { id: number; name: string }) {
    this.citySelected = city;
    for (let i = 0; i < this.citiesSelected.value.length; i++) {
      if (city.id === this.citiesSelected.value[i].id)
        return;
    }
    (<FormArray>this.chooseCityForm.get('cities')).push(new FormControl(city));
  }

  deleteCity(index: number) {
    this.citiesSelected.removeAt(index);
    let len = this.citiesSelected.value.length;
    if (len == 0) {
      this.citySelected = {id: -1, name: ''};
    } else {
      this.citySelected =
        this.citiesSelected.value[this.citiesSelected.length - 1];
    }
  }

  onSubmit() {
    if (!this.chooseCityForm.valid) {
      this.chooseCityForm.markAllAsTouched();
      return;
    }
    this.citiesChosen.emit(this.chooseCityForm.value.cities);
  }
}
