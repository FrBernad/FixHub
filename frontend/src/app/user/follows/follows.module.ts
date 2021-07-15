import {NgModule} from "@angular/core";
import {FollowerCardComponent} from "./follower-card/follower-card.component";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    FollowerCardComponent
  ],
  imports: [
    RouterModule,
    SharedModule,
  ],
  exports: [
    FollowerCardComponent
  ]
})
export class FollowsModule {
}
