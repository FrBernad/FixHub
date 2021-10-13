import {VerifyComponent} from "./verify.component";
import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {NotVerifiedOrUnauthGuard} from "../guards/not-verified-or-unauth.guard";

const routes: Routes = [
  {
    path: '',
    component: VerifyComponent,
    canActivate: [NotVerifiedOrUnauthGuard],
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VerifyRoutingModule {
}
