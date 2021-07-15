import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {LandingPageLayoutComponent} from "./landing-page-layout.component";
import {LandingPageComponent} from "../../landing-page/landing-page.component";
import {StartGuard} from "../../auth/guards/start.guard";

const routes: Routes = [
  {
    path: '',
    component: LandingPageLayoutComponent,
    canActivate: [StartGuard],
    children: [
      {
        path: '',
        component: LandingPageComponent,
        loadChildren: () => import("../../landing-page/landing-page.module").then(m => m.LandingPageModule)
      }
    ]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LandingPageLayoutRoutingModule {
}
