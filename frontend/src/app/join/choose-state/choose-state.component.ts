import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {JobService} from "../../job/job.service";
import {JobsService, State} from "../../discover/jobs.service";
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
    private jobsService: JobsService
  ) {
  }

  states: State[];
  @Input() user: User;

  ngOnInit(): void {

    if (this.isProvider) {
      this.chooseStateForm = new FormGroup({
        'startTime': new FormControl(this.user.providerDetails.schedule.startTime, [Validators.required, Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
        'endTime': new FormControl(this.user.providerDetails.schedule.endTime, [Validators.required, Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
        'state': new FormControl(this.user.providerDetails.location.state, [Validators.required])
      });
      this.stateSelected = this.user.providerDetails.location.state.name;
    } else {
      this.chooseStateForm = new FormGroup({
        'startTime': new FormControl(null, [Validators.required, Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
        'endTime': new FormControl(null, [Validators.required, Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
        'state': new FormControl(null, [Validators.required])
      });
    }

    this.jobsService.getStates().subscribe(
      (states) => {
        console.log("bbb")
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
