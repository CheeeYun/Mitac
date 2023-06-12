import { NgModule } from '@angular/core';
import { HeroesComponent } from './heroes/heroes.component';
import { RouterModule,Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
const routes:Routes=[
  {path:'heroes',component:HeroesComponent}, //path:用來匹配瀏覽器url
  {path:'dashboard',component:DashboardComponent},
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'detail/:id', component: HeroDetailComponent }
];              //component:導航到該路由時建立的原元件
@NgModule({ //forRoot() 方法會提供路由所需的服務提供者和指令，還會基於瀏覽器的當前 URL 執行首次導航
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
