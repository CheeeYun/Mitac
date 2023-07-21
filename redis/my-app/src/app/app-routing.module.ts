import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MypageComponent } from './mypage/mypage.component';
import { AuthGuard } from './authGuard';

const routes: Routes = [
  { path:'',redirectTo:'/login',pathMatch:'full'},
  { path:'login',component: LoginComponent},
  { path:'mypage',component: MypageComponent,canActivate:[AuthGuard]},
  { path:'**',redirectTo:'/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
