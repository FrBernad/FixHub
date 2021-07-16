import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "../../auth/guards/auth.guard";
import {NotificationsComponent} from "./notifications.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
  {
    canActivate: [AuthGuard],
    path: '',
    component: NotificationsComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NotificationsRoutingModule {
}
