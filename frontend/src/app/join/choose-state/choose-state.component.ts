import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-choose-state',
  templateUrl: './choose-state.component.html',
  styleUrls: ['./choose-state.component.scss']
})
export class ChooseStateComponent implements OnInit {

  chooseStateForm: FormGroup;
  stateSelected = '';
  @Output() stateChosen = new EventEmitter<void>();

  @Input()
  public isEdit: boolean;

  states = [{id:1,name:'Buenos Aires'},{id:2,name:'CABA'}];
  constructor() { }

  providerDetails = {
    location: {
      cities: [{id:1, name:'Martinez'}, {id:2, name:'Adrogue'}, {id:3, name:'Pilar'}, {id:4, name:'CABA'}],
      state: { id: 1, name: 'Buenos Aires' }
    },
    schedule: {
      startTime: '11:00 AM',
      endTime: '10:00 PM'
    }
  };

  ngOnInit(): void {
    if(this.isEdit != null && this.isEdit){
      this.chooseStateForm = new FormGroup({
        'startTime' : new FormControl(this.providerDetails.schedule.startTime,[Validators.required,Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
        'endTime' : new FormControl(this.providerDetails.schedule.endTime,[Validators.required,Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
        'state' : new FormControl(this.providerDetails.location.state,[Validators.required])
      });
      this.stateSelected = this.providerDetails.location.state.name;
    }else {
    this.chooseStateForm = new FormGroup({
      'startTime' : new FormControl(null,[Validators.required,Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
      'endTime' : new FormControl(null,[Validators.required,Validators.pattern('((([1-9])|(1[0-2])):([0-5])([0-9]) (A|P)M)')]),
      'state' : new FormControl(null,[Validators.required])
    });
  }
  }

  onSubmit(){
    if(!this.chooseStateForm.valid){
      this.chooseStateForm.markAllAsTouched();
      return;
    }
    console.log(this.chooseStateForm);
    this.stateChosen.emit();
  }

  selectState(state:{id:number, name:string}){
    this.stateSelected = state.name;
    this.chooseStateForm.patchValue({'state': state});
  }
}
