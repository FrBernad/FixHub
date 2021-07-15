import {ErrorsComponent} from "./errors.component";
import {SharedModule} from "../shared/shared.module";
import {NgModule} from "@angular/core";
import {ErrorsRoutingModule} from "./errors-routing.module";

@NgModule({
  declarations: [
    ErrorsComponent,
  ],
  imports: [
    SharedModule,
    ErrorsRoutingModule
  ],
  exports: [
    ErrorsComponent,
  ]
})
export class ErrorsModule {
}
