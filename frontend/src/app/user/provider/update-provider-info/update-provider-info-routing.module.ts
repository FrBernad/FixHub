import {ProviderGuard} from "../../../auth/guards/provider.guard";
import {RouterModule, Routes} from "@angular/router";
import {UpdateProviderInfoComponent} from "./update-provider-info.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
  {
    path: '',
    component: UpdateProviderInfoComponent,
    canActivate: [ProviderGuard],
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UpdateProviderInfoRoutingModule {
}
