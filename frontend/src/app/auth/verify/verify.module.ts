import {NgModule} from "@angular/core";
import {VerifyComponent} from "./verify.component";
import {SharedModule} from "../../shared/shared.module";
import {VerifyRoutingModule} from "./verify-routing.module";

@NgModule({
  declarations: [
    VerifyComponent,
  ],
  imports: [
    SharedModule,
    VerifyRoutingModule
  ],
  exports: [
    VerifyComponent
  ]
})
export class VerifyModule{}
