import { Component,OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { HttpClient, HttpHeaders,HttpResponse} from'@angular/common/http';

import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit{
  username: string = "";
  password: string = "";
  Rusername: string = "";
  Rpassword: string = "";
  jwt: string="";
  Isregister:boolean= false;

  constructor(private router:Router,
              private http: HttpClient,
              ){}

  ngOnInit(){

  }
    login(){
    const loginData={
      username: this.username,
      password: this.password
    }
  
    console.log(loginData);
   
    return this.http.post('http://localhost:8080/api/login', loginData, { observe: 'response' })
  .subscribe(
    (response: HttpResponse<any>) => {
      const body = response.body;
      this.jwt=response.body.jwt;

      const headers=new HttpHeaders().set('Authorization', 'Bearer ' + this.jwt);
      console.log(headers.get('Authorization'));
      // console.log(headers);
      
      sessionStorage.setItem('jwt',this.jwt);
      sessionStorage.setItem('username',response.body.username);
      console.log(body);
      this.router.navigate(['/mypage']);
    },
    (error) => {
      console.log("Login fail", error);
    }
  );
  }
  register(){
    this.Isregister=true;

  }
  enter(){
    this.Isregister=false;
    const registerData={
      username:this.Rusername,
      password:this.Rpassword
    }
    console.log(registerData);

    return this.http.post('http://localhost:8080/api/register', registerData, { observe: 'response' })
    .subscribe(
      (response) => {
        console.log('Register success:', response);
        alert("註冊成功")
        // 执行其他处理逻辑，比如跳转页面等
      },
      (error) => {
        console.log('Register error:', error);
        alert("註冊失敗")
        // 执行错误处理逻辑
      }
    );
  }
}
