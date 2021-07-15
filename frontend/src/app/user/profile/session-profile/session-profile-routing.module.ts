import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {SessionProfileComponent} from "./session-profile.component";
import {AuthGuard} from "../../../auth/guards/auth.guard";

const routes: Routes = [
  {
    canActivate: [AuthGuard],
    path: '',
    component: SessionProfileComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SessionProfileRoutingModule {
}
