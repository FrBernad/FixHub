import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {FollowersComponent} from "./followers.component";

const routes: Routes = [
  {
    path: '',
    component: FollowersComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FollowersRoutingModule {
}
