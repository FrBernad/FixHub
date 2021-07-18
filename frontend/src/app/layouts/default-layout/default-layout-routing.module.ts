import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {DefaultLayoutComponent} from "./default-layout.component";
import {StartGuard} from "../../auth/guards/start.guard";

const routes: Routes = [
  {
    path: '',
    component: DefaultLayoutComponent,
    canActivate: [StartGuard],
    children: [
      {
        path: 'discover',
        loadChildren: () => import("../../discover/discover.module").then(m => m.DiscoverModule)
      },
      {
        path: 'user/profile',
        loadChildren: () => import("../../user/profile/session-profile/session-profile.module")
          .then(m => m.SessionProfileModule)
      },
      {
        path: 'user/dashboard',
        loadChildren: () => import("../../user/provider/dashboard/dashboard.module")
          .then(m => m.DashboardModule)
      },
      {
        path: 'user/notifications',
        loadChildren: () => import("../../user/notifications/notifications.module")
          .then(m => m.NotificationsModule)
      },
      {
        path: 'user/requests/:id',
        loadChildren: () => import("../../user/requests/request-detail/request-detail.module")
          .then(m => m.RequestDetailModule)
      },
      {
        path: 'user/requests',
        loadChildren: () => import("../../user/requests/requests.module").then(m => m.RequestsModule)
      },
      {
        path: 'user/join',
        loadChildren: () => import("../../auth/join/join.module").then(m => m.JoinModule)
      },
      {
        path: 'user/account/updateProviderInfo',
        loadChildren: () => import("../../user/provider/update-provider-info/update-provider-info.module")
          .then(m => m.UpdateProviderInfoModule)
      },
      {
        path: 'user/verify',
        loadChildren: () => import("../../auth/verify/verify.module").then(m => m.VerifyModule)
      },
      {
        path: 'user/resetPassword',
        loadChildren: () => import("../../auth/reset-password/reset-password.module").then(m => m.ResetPasswordModule)
      },
      {
        path: 'user/:id',
        loadChildren: () => import("../../user/profile/user-profile/user-profile.module")
          .then(m => m.UserProfileModule)
      },
      {
        path: 'user/:id/followers',
        loadChildren: () => import("../../user/follows/followers/followers.module").then(m => m.FollowersModule)
      },
      {
        path: 'user/:id/following',
        loadChildren: () => import("../../user/follows/following/following.module").then(m => m.FollowingModule)
      },
      {
        path: 'login',
        loadChildren: () => import("../../auth/login/login.module").then(m => m.LoginModule)
      },
      {
        path: 'register',
        loadChildren: () => import("../../auth/register/register.module").then(m => m.RegisterModule)
      },
      {
        path: "jobs",
        loadChildren: () => import("../../job/job.module").then(m => m.JobModule)
      },
      {
        path: '404',
        loadChildren: () => import("../../errors/errors.module").then(m => m.ErrorsModule),
        data: {
          i18: 'pageNotFound',
          code: 404,
        }
      },
      {
        path: '500',
        loadChildren: () => import("../../errors/errors.module").then(m => m.ErrorsModule),
        data: {
          i18: 'serverError',
          code: 500,
        }
      },
      {
        path: '**',
        loadChildren: () => import("../../errors/errors.module").then(m => m.ErrorsModule),
        data: {
          i18: 'pageNotFound',
          code: 404,
        }
      }
    ]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DefaultLayoutRoutingModule {
}
