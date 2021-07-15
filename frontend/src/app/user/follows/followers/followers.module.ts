import {SharedModule} from "../../../shared/shared.module";
import {FollowsModule} from "../follows.module";
import {NgModule} from "@angular/core";
import {FollowersComponent} from "./followers.component";
import {FollowersRoutingModule} from "./followers-routing.module";

@NgModule({
  declarations: [
    FollowersComponent,
  ],
  imports: [
    FollowsModule,
    SharedModule,
    FollowersRoutingModule
  ],
  exports: [
    FollowersComponent
  ]
})
export class FollowersModule {
}
