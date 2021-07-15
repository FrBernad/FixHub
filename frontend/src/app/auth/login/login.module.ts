import {LoginComponent} from "./login.component";
import {RecoverEmailPopupComponent} from "./recover-email-popup/recover-email-popup.component";
import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {LoginRoutingModule} from "./login-routing.module";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    RecoverEmailPopupComponent,
    LoginComponent,
  ],
  imports: [
    SharedModule,
    ReactiveFormsModule,
    LoginRoutingModule
  ],
  exports: [
    LoginComponent
  ]
})
export class LoginModule{}
