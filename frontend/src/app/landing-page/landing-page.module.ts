import {NgModule} from "@angular/core";
import {LandingPageComponent} from "./landing-page.component";
import {PopularJobCardComponent} from "./popular-job-card/popular-job-card.component";
import {SharedModule} from "../shared/shared.module";
import {LandingPageRoutingModule} from "./landing-page-routing-module";
import {RouterModule} from "@angular/router";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    LandingPageComponent,
    PopularJobCardComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    LandingPageRoutingModule
  ],
  exports: [
    LandingPageComponent
  ]
})
export class LandingPageModule {
}
