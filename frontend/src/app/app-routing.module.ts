import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DiscoverComponent } from './discover/discover.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {path: "",component: HomeComponent},
  {path: "/discover",component:DiscoverComponent},
  {path: "/user",component:ProfileComponent},
  {path: "/user/dashboard",component:DashboardComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
