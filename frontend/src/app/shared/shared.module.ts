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

@NgModule({
  declarations: [
    LoadingSpinnerComponent,
    PaginationComponent,
    NavbarComponent,
    FooterComponent,
    JobCardComponent,
  ],
  imports: [
    FontAwesomeModule,
    RouterModule,
    TranslateModule,
    CommonModule
  ],
  exports: [
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
