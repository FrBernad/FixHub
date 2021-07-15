import {NgModule} from "@angular/core";
import {RegisterComponent} from "./register.component";
import {SharedModule} from "../../shared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import {RegisterRoutingModule} from "./register.routing.module";

@NgModule({
  declarations: [
    RegisterComponent,
  ],
  imports: [
    SharedModule,
    ReactiveFormsModule,
    RegisterRoutingModule
  ],
  exports: [
    RegisterComponent
  ]
})
export class RegisterModule{}
