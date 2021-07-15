import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ProfileGuard} from "../../../auth/guards/profile.guard";
import {UserProfileComponent} from "./user-profile.component";

const routes: Routes = [
  {
    canActivate: [ProfileGuard],
    path: '',
    component: UserProfileComponent,
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserProfileRoutingModule {
}
