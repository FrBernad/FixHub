import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";
import {RequestsComponent} from "./requests/requests.component";
import {WorksComponent} from "./works/works.component";
import {SharedModule} from "../../../shared/shared.module";
import {DashboardRoutingModule} from "./dashboard-routing.module";

@NgModule({
  declarations: [
    DashboardComponent,
    RequestsComponent,
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
