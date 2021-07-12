import {UpdateProviderInfoComponent} from './update-provider-info/update-provider-info.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {DiscoverComponent} from './discover/discover.component';
import {LoginComponent} from './login/login.component';
import {SessionProfileComponent} from './profile/session-profile/session-profile.component';
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
import {NotProviderGuard} from "./auth/not-provider.guard";
import {FollowersComponent} from "./followers/followers.component";
import {FollowingComponent} from "./followers/following.component";
import {UserProfileComponent} from "./profile/user-profile/user-profile.component";
import {VerifyComponent} from "./verify/verify.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";

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
        component: DiscoverComponent,
      },
      {
        path: 'user/profile',
        component: SessionProfileComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'user/dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard, ProviderGuard]
      },
      {
        path: 'user/join',
        component: JoinComponent,
        canActivate: [AuthGuard]
        // canActivate: [AuthGuard, NotProviderGuard]
      },
      {
        path: 'user/account/updateProviderInfo',
        component: UpdateProviderInfoComponent
      },
      {
        path: 'user/verify',
        component: VerifyComponent,
      },
      {
        path: 'user/resetPassword',
        component: ResetPasswordComponent,
      },
      {
        path: 'user/:id',
        component: UserProfileComponent,
      },
      {
        path: 'user/:id/followers',
        component: FollowersComponent
      },
      {
        path: 'user/:id/following',
        component: FollowingComponent
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
        path: "job",
        component: JobComponent
      },
      {
        path: "jobs/new",
        component: NewJobComponent
      },
      {
        path: "jobs/:id",
        component: JobComponent
      },
      {
        path: 'jobs/:id/contact',
        component: ContactComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'jobs/:id/edit',
        component: EditJobComponent
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
  exports: [RouterModule],
})
export class AppRoutingModule {
}
