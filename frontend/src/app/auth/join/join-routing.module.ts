import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {JoinComponent} from "./join.component";
import {AuthGuard} from "../guards/auth.guard";
import {VerifiedGuard} from "../guards/verified.guard";
import {NotProviderGuard} from "../guards/not-provider.guard";

const routes: Routes = [
  {
    path: '',
    component: JoinComponent,
    canActivate: [AuthGuard, VerifiedGuard, NotProviderGuard]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JoinRoutingModule {
}
