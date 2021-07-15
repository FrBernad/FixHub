import {RouterModule, Routes} from "@angular/router";
import {UnauthGuard} from "../guards/unauth.guard";
import {RegisterComponent} from "./register.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
  {
    path: '',
    component: RegisterComponent,
    canActivate: [UnauthGuard],
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegisterRoutingModule {
}
