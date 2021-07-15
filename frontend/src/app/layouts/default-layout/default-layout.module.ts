import {NgModule} from "@angular/core";
import {DefaultLayoutComponent} from "./default-layout.component";
import {SharedModule} from "../../shared/shared.module";
import {DefaultLayoutRoutingModule} from "./default-layout-routing.module";

@NgModule({
  declarations: [
    DefaultLayoutComponent,
  ],
  imports: [
    SharedModule,
    DefaultLayoutRoutingModule,
  ],
  exports: [
    DefaultLayoutComponent
  ]
})
export class DefaultLayoutModule {
}
