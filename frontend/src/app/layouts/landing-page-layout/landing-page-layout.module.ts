import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {LandingPageLayoutComponent} from "./landing-page-layout.component";
import {LandingPageLayoutRoutingModule} from "./landing-page-layout-routing.module";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    LandingPageLayoutComponent,
  ],
  imports: [
    SharedModule,
    RouterModule,
    LandingPageLayoutRoutingModule,
  ],
  exports: [
    LandingPageLayoutComponent
  ]
})
export class LandingPageLayoutModule {
}
