import {NgModule} from "@angular/core";
import {RequestComponent} from "./request-detail.component";
import {SharedModule} from "../../../shared/shared.module";
import {RequestDetailRoutingModule} from "./request-detail-routing.module";

@NgModule({
  declarations: [
    RequestComponent,
  ],
  imports: [
    SharedModule,
    RequestDetailRoutingModule,
  ],
  exports: [
    RequestComponent,
  ]
})
export class RequestDetailModule {
}
