import {LoadingSpinnerComponent} from "./loading-spinner/loading-spinner.component";
import {NgModule} from "@angular/core";
import {PaginationComponent} from "./pagination/pagination.component";
import {CommonModule} from "@angular/common";
import {TranslateModule} from "@ngx-translate/core";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {NavbarComponent} from "./navbar/navbar.component";
import {JobCardComponent} from "./job-card/job-card.component";
import {FooterComponent} from "./footer/footer.component";
import {RouterModule} from "@angular/router";
import {ChooseCityComponent} from "./choose-city/choose-city.component";
import {ChooseStateComponent} from "./choose-state/choose-state.component";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    ChooseCityComponent,
    ChooseStateComponent,
    LoadingSpinnerComponent,
    PaginationComponent,
    NavbarComponent,
    FooterComponent,
    JobCardComponent,
  ],
  imports: [
    ReactiveFormsModule,
    NgxMaterialTimepickerModule,
    FontAwesomeModule,
    RouterModule,
    TranslateModule,
    CommonModule
  ],
  exports: [
    ChooseCityComponent,
    ChooseStateComponent,
    CommonModule,
    TranslateModule,
    FontAwesomeModule,
    LoadingSpinnerComponent,
    PaginationComponent,
    NavbarComponent,
    FooterComponent,
    JobCardComponent
  ]
})
export class SharedModule {
}
