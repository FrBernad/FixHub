import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DiscoverService, State} from "../../discover/discover.service";
import {User} from "../../models/user.model";

@Component({
  selector: 'app-choose-state',
  templateUrl: './choose-state.component.html',
  styleUrls: ['./choose-state.component.scss']
})
export class ChooseStateComponent implements OnInit {

  chooseStateForm: FormGroup;
  stateSelected = '';
  isFetching = true;

  @Output() stateChosen = new EventEmitter<State>();

  @Input() public isProvider: boolean;

  constructor(
    private jobsService: DiscoverService
  ) {
  }

  states: State[];
  @Input() user: User;

  ngOnInit(): void {
    if (this.isProvider) {
      this.chooseStateForm = new FormGroup({
        'startTime': new FormControl(this.user.providerDetails.schedule.startTime, [Validators.required, Validators.pattern('^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$')]),
        'endTime': new FormControl(this.user.providerDetails.schedule.endTime, [Validators.required, Validators.pattern('^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$')]),
        'state': new FormControl(this.user.providerDetails.location.state, [Validators.required])
      });
      this.stateSelected = this.user.providerDetails.location.state.name;
    } else {
      this.chooseStateForm = new FormGroup({
        'startTime': new FormControl(null, [Validators.required, Validators.pattern('^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$')]),
        'endTime': new FormControl(null, [Validators.required, Validators.pattern('^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$')]),
        'state': new FormControl(null, [Validators.required])
      });
    }

    this.jobsService.getStates().subscribe(
      (states) => {
        this.states = states
        this.isFetching = false;
      }
    );
  }

  onSubmit() {
    if (!this.chooseStateForm.valid) {
      this.chooseStateForm.markAllAsTouched();
      return;
    }
    this.stateChosen.emit(this.chooseStateForm.value);
  }

  selectState(state: { id: number, name: string }) {
    this.stateSelected = state.name;
    this.chooseStateForm.patchValue({'state': state});
  }
}
