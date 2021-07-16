import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AuthGuard} from "../../../auth/guards/auth.guard";
import {RequestComponent} from "./request-detail.component";

const routes: Routes = [
  {
    path: '',
    component: RequestComponent,
    canActivate: [AuthGuard],
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RequestDetailRoutingModule {
}
