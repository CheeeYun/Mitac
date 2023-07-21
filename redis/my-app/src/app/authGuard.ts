import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    if (sessionStorage.getItem('jwt')) {
      // 已登录，允许访问
      return true;
    } else {
      // 未登录，重定向到登录页
      this.router.navigate(['/login']);
      alert("請登入")
      return false;
    }
  }
}
