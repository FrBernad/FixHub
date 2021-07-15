import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {JoinComponent} from "./join.component";
import {JoinRoutingModule} from "./join-routing.module";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";

@NgModule({
  declarations: [
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
