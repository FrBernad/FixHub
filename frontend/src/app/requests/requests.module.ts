import {NgModule} from "@angular/core";
import {UserSentRequestCardComponent} from "./user-sent-request-card/user-sent-request-card.component";
import {SharedModule} from "../shared/shared.module";
import {RequestsComponent} from "./requests.component";
import {RequestsRoutingModule} from "./requests-routing.module";

@NgModule({
  declarations: [
    UserSentRequestCardComponent,
    RequestsComponent,
  ],
  imports: [
    SharedModule,
    RequestsRoutingModule
  ],
  exports: [
    RequestsComponent
  ]
})
export class RequestsModule{}
