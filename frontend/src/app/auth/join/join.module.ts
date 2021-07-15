import {NgModule} from "@angular/core";
import {ChooseCityComponent} from "./choose-city/choose-city.component";
import {ChooseStateComponent} from "./choose-state/choose-state.component";
import {SharedModule} from "../../shared/shared.module";
import {JoinComponent} from "./join.component";
import {JoinRoutingModule} from "./join-routing.module";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";

@NgModule({
  declarations: [
    ChooseCityComponent,
    ChooseStateComponent,
    JoinComponent
  ],
  imports: [
    NgxMaterialTimepickerModule,
    SharedModule,
    JoinRoutingModule
  ],
  exports: [
    JoinComponent
  ]
})
export class JoinModule{}
