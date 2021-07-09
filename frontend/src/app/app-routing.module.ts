import { UpdateInfoComponent } from './update-info/update-info.component';
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
import {ContactComponent} from './job/contact/contact.component';
import {JobComponent} from "./job/job.component";
import {AuthGuard} from "./auth/auth.guard";
import {EditJobComponent} from "./job/edit-job/edit-job.component";
import {NewJobComponent} from "./job/new-job/new-job.component";
import {ErrorsComponent} from "./errors/errors.component";
import {JoinComponent} from "./join/join.component";
import {UnauthGuard} from "./auth/unauth.guard";
import {ProviderGuard} from "./auth/provider.guard";

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
      {
        path: 'discover',
        component: DiscoverComponent
      },
      {
        path: 'user/profile',
        component: ProfileComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'user/dashboard',
        component: DashboardComponent,
        // canActivate: [AuthGuard, ProviderGuard]
      },
      {
        path: 'login',
        component: LoginComponent,
        canActivate: [UnauthGuard],
        data: {title: "login.title"}
      },
      {
        path: 'register',
        component: RegisterComponent
      },
      {
        path: 'jobs/:jobId/contact',
        component: ContactComponent,
        canActivate: [AuthGuard]
      },
      {
        path: "job",
        component: JobComponent
      },
      {
        path: "jobs/new",
        component: NewJobComponent
      },
      {
        path: "jobs/:jobId",
        component: JobComponent
      },
      {
        path: 'jobs/:jobId/edit',
        component: EditJobComponent
      },
      {
        path: 'user/join',
        component: JoinComponent
      },
      {
        path: 'user/account/updateInfo',
        component: UpdateInfoComponent
      },
      {
        path: '**',
        component: ErrorsComponent
      }
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
