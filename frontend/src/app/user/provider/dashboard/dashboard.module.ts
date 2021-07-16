import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";
import {WorksComponent} from "./works/works.component";
import {SharedModule} from "../../../shared/shared.module";
import {DashboardRoutingModule} from "./dashboard-routing.module";

@NgModule({
  declarations: [
    DashboardComponent,
    WorksComponent,
  ],
  imports: [
    SharedModule,
    DashboardRoutingModule,
  ],
  exports: [
    DashboardComponent
  ]
})
export class DashboardModule {
}
