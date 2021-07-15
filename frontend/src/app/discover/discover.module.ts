import {NgModule} from "@angular/core";
import {DiscoverComponent} from "./discover.component";
import {SharedModule} from "../shared/shared.module";
import {DiscoverRoutingModule} from "./discover-routing.module";

@NgModule({
  declarations: [
    DiscoverComponent,
  ],
  imports: [
    SharedModule,
    DiscoverRoutingModule
  ],
  exports: [
    DiscoverComponent
  ]
})
export class DiscoverModule{}
