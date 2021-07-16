import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {RequestsComponent} from "./requests.component";
import {RequestsRoutingModule} from "./requests-routing.module";
import {SentRequestsComponent} from "./sent-requests/sent-requests.component";
import {RequestCardComponent} from "./request-card/request-card.component";
import {ReceivedRequestsComponent} from "./received-requests/received-requests.component";

@NgModule({
  declarations: [
    SentRequestsComponent,
    ReceivedRequestsComponent,
    RequestCardComponent,
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
