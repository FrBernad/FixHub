import {NgModule} from "@angular/core";
import {UpdateInfoComponent} from "./update-info/update-info.component";
import {SessionProfileComponent} from "./session-profile.component";
import {SharedModule} from "../../../shared/shared.module";
import {SessionProfileRoutingModule} from "./session-profile-routing.module";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    UpdateInfoComponent,
    SessionProfileComponent,
  ],
  imports: [
    SharedModule,
    ReactiveFormsModule,
    SessionProfileRoutingModule
  ],
  exports: [
    SessionProfileComponent,
  ]
})
export class SessionProfileModule {
}
