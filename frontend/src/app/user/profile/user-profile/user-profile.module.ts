import {NgModule} from "@angular/core";
import {SharedModule} from "../../../shared/shared.module";
import {UserProfileComponent} from "./user-profile.component";
import {UserProfileRoutingModule} from "./user-profile-routing.module";

@NgModule({
  declarations: [
    UserProfileComponent
  ],
  imports: [
    SharedModule,
    UserProfileRoutingModule
  ],
  exports: [
    UserProfileComponent
  ]
})
export class UserProfileModule {
}
