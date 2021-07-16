import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {RequestsComponent} from "./requests.component";
import {RequestsRoutingModule} from "./requests-routing.module";
import {SentRequestsComponent} from "./sent-requests/sent-requests.component";
import {ReceivedRequestCardComponent} from "./received-requests/received-request-card/received-request-card.component";
import {ReceivedRequestsComponent} from "./received-requests/received-requests.component";
import {SentRequestCardComponent} from "./sent-requests/sent-request-card/sent-request-card.component";

@NgModule({
  declarations: [
    SentRequestsComponent,
    SentRequestCardComponent,
    ReceivedRequestCardComponent,
    ReceivedRequestsComponent,
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
