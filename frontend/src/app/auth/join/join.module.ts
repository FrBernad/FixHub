import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {JoinComponent} from "./join.component";
import {JoinRoutingModule} from "./join-routing.module";

@NgModule({
  declarations: [
    JoinComponent
  ],
  imports: [
    SharedModule,
    JoinRoutingModule
  ],
  exports: [
    JoinComponent
  ]
})
export class JoinModule{}
