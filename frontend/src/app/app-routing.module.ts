import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DiscoverComponent } from './discover/discover.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  {path: "",component: HomeComponent},
  {path: "discover",component:DiscoverComponent},
  {path: "user/profile",component:ProfileComponent},
  {path: "user/dashboard",component:DashboardComponent},
  {path: "login",component:LoginComponent},
  {path: "register",component:RegisterComponent},
  // {path:"**",}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
