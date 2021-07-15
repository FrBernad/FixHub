import {NgModule} from "@angular/core";
import {ContactComponent} from "./contact/contact.component";
import {EditJobComponent} from "./edit-job/edit-job.component";
import {NewJobComponent} from "./new-job/new-job.component";
import {ReviewCardComponent} from "./review-card/review-card.component";
import {ReviewFormComponent} from "./review-form/review-form.component";
import {ReviewPaginationComponent} from "./review-pagination/review-pagination.component";
import {JobComponent} from "./job.component";
import {SharedModule} from "../shared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import {JobRoutingModule} from "./job-routing.module";

@NgModule({
  declarations: [
    ContactComponent,
    EditJobComponent,
    NewJobComponent,
    ReviewCardComponent,
    ReviewFormComponent,
    ReviewPaginationComponent,
    JobComponent
  ],
  imports: [
    SharedModule,
    ReactiveFormsModule,
    JobRoutingModule,
  ],
  exports: [
    JobComponent
  ]
})
export class JobModule {
}
