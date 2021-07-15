import {FollowingComponent} from "./following.component";
import {SharedModule} from "../../../shared/shared.module";
import {FollowsModule} from "../follows.module";
import {NgModule} from "@angular/core";
import {FollowingRoutingModule} from "./following-routing.module";

@NgModule({
  declarations: [
    FollowingComponent,
  ],
  imports: [
    FollowsModule,
    SharedModule,
    FollowingRoutingModule
  ],
  exports: [
    FollowingComponent
  ]
})
export class FollowingModule {
}
