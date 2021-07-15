import {ResetPasswordComponent} from "./reset-password.component";
import {NgModule} from "@angular/core";
import {ReactiveFormsModule} from "@angular/forms";
import {ResetPasswordRoutingModule} from "./reset-password-routing.module";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    ResetPasswordComponent,
  ],
  imports: [
    ReactiveFormsModule,
    SharedModule,
    ResetPasswordRoutingModule
  ],
  exports: [
    ResetPasswordComponent,
  ]
})
export class ResetPasswordModule {
}
