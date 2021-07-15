import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import("./layouts/landing-page-layout/landing-page-layout.module").then(m => m.LandingPageLayoutModule)
  },
  {
    path: '',
    loadChildren: () =>
      import("./layouts/default-layout/default-layout.module").then(m => m.DefaultLayoutModule)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
