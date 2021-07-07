import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {DiscoverComponent} from './discover/discover.component';
import {LoginComponent} from './login/login.component';
import {ProfileComponent} from './profile/profile.component';
import {RegisterComponent} from './register/register.component';
import {LandingPageLayoutComponent} from "./layouts/landing-page-layout/landing-page-layout.component";
import {LandingPageComponent} from "./landing-page/landing-page.component";
import {DefaultLayoutComponent} from "./layouts/default-layout/default-layout.component";
import { ContactComponent } from './contact/contact.component';
import {JobComponent} from "./job/job.component";

const routes: Routes = [
  {
    path: '',
    component: LandingPageLayoutComponent,
    children: [
      {
        path: '',
        component: LandingPageComponent
      }
    ]
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    children: [
      {path: 'discover', component: DiscoverComponent},
      {path: 'user/profile', component: ProfileComponent},
      {path: 'user/dashboard', component: DashboardComponent},
      {path: 'login', component: LoginComponent},
      {path: 'jobs/:jobId/contact', component: ContactComponent},
    {path: "job", component:JobComponent}
    ]
  },
 
  // {path:"**",}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
