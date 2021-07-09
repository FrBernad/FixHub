import {Component, EventEmitter, OnInit, Output} from '@angular/core';
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

  states = [{id:1,name:'Buenos Aires'},{id:2,name:'CABA'}];
  constructor() { }

  ngOnInit(): void {
    this.chooseStateForm = new FormGroup({
      'startTime' : new FormControl(null,[Validators.required,Validators.pattern('([0-9]|[10-12]):([0-5][0-9]) (AM|PM)')]),
      'endTime' : new FormControl(null,[Validators.required,Validators.pattern('([0-9]|[10-12]):([0-5][0-9]) (AM|PM)')]),
      'state' : new FormControl(null,[Validators.required])
    });
  }

  onSubmit(){
    if(!this.chooseStateForm.valid){
      this.chooseStateForm.markAllAsTouched();
      return;
    }
    this.stateChosen.emit();
  }

  selectState(state:{id:number, name:string}){
    this.stateSelected = state.name;
    this.chooseStateForm.patchValue({'state': state});
  }
}
