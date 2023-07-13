import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authenticated =false;
  constructor(private http: HttpClient , private router :Router){}
  
  login(username: string, password: string): void {
    const member = { username: username, password: password };
    const url ="http://localhost:8080/checkMember";
    this.http.post<boolean>(url, member).subscribe(
      (response) => {
        if (response ) {
          this.authenticated =true;
          console.log('登录成功');
          this.router.navigate(['/dashboard']);
        } else {
          this.authenticated =false
          console.log('登录失败');
        }
      },
      (error) => {
        this.authenticated =false
        console.error('登录请求发生错误', error);
      }
    );
  }
  logout(): void {
    alert('logout')
    this.authenticated = false;
    location.reload()
  }
  
  isAuthenticated(): boolean {
   
    return this.authenticated;
  }
  

  
}

