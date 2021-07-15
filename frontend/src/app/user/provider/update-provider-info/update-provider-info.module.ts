import {SharedModule} from "../../../shared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core";
import {UpdateProviderInfoRoutingModule} from "./update-provider-info-routing.module";
import {UpdateProviderInfoComponent} from "./update-provider-info.component";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";

@NgModule({
  declarations: [
    UpdateProviderInfoComponent,
  ],
  imports: [
    NgxMaterialTimepickerModule,
    SharedModule,
    ReactiveFormsModule,
    UpdateProviderInfoRoutingModule
  ],
  exports: [
    UpdateProviderInfoComponent,
  ]
})
export class UpdateProviderInfoModule {
}
