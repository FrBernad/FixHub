import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login.component";
import {UnauthGuard} from "../guards/unauth.guard";
import {NgModule} from "@angular/core";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    canActivate: [UnauthGuard],
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule {
}
