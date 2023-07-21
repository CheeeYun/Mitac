import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // 导入 FormsModule
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { MypageComponent } from './mypage/mypage.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MypageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule ,// 添加 FormsModule
    HttpClientModule,
    ReactiveFormsModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
