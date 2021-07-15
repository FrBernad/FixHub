import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";
import {RequestComponent} from "./request/request.component";
import {WorksComponent} from "./works/works.component";
import {SharedModule} from "../../../shared/shared.module";
import {RequestJobCardComponent} from "./request/request-job-card/request-job-card.component";
import {DashboardRoutingModule} from "./dashboard-routing.module";

@NgModule({
  declarations: [
    DashboardComponent,
    RequestComponent,
    WorksComponent,
    RequestJobCardComponent
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
