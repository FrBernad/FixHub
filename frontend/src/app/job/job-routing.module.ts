import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {JobComponent} from "./job.component";
import {ProviderGuard} from "../auth/guards/provider.guard";
import {NewJobComponent} from "./new-job/new-job.component";
import {EditJobComponent} from "./edit-job/edit-job.component";

const routes: Routes = [
  {
    path: "new",
    component: NewJobComponent,
    canActivate: [ProviderGuard]
  },
  {
    path: ':id',
    component: JobComponent,
  },
  {
    path: ':id/edit',
    canActivate: [ProviderGuard],
    component: EditJobComponent
  },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JobRoutingModule {
}
