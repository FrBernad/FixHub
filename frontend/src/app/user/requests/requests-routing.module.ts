import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {RequestsComponent} from "./requests.component";
import {AuthGuard} from "../../auth/guards/auth.guard";
import {VerifiedGuard} from "../../auth/guards/verified.guard";

const routes: Routes = [
  {
    path: '',
    component: RequestsComponent,
    canActivate: [AuthGuard, VerifiedGuard],
  }
]


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RequestsRoutingModule {
}
