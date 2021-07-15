import {NgModule} from "@angular/core";
import {RequestComponent} from "./request.component";
import {SharedModule} from "../../../shared/shared.module";
import {RequestRoutingModule} from "./request-routing.module";

@NgModule({
  declarations: [
    RequestComponent,
  ],
  imports: [
    SharedModule,
    RequestRoutingModule,
  ],
  exports: [
    RequestComponent,
  ]
})
export class RequestModule {
}
