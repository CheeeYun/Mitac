// import { NgModule } from '@angular/core';
// import { RouterModule,Routes } from '@angular/router';
// import { DashboardComponent } from './dashboard/dashboard.component';
// import { HeroesComponent } from './heroes/heroes.component';
// import { HeroDetailComponent } from './hero-detail/hero-detail.component';
// const routes:Routes=[
//   { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
//   {path:'dashboard',component:DashboardComponent},
//   { path: 'detail/:id', component: HeroDetailComponent },
//   {path:'heroes',component:HeroesComponent} //path:用來匹配瀏覽器url
// ];              //component:導航到該路由時建立的原元件
// @NgModule({ //forRoot() 方法會提供路由所需的服務提供者和指令，還會基於瀏覽器的當前 URL 執行首次導航
//   imports: [RouterModule.forRoot(routes)],
//   exports: [RouterModule]
// })
// export class AppRoutingModule { }
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: HeroDetailComponent },
  { path: 'heroes', component: HeroesComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}


/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at https://angular.io/license
*/