import { Component,OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse} from'@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-mypage',
  templateUrl: './mypage.component.html',
  styleUrls: ['./mypage.component.css']
})
export class MypageComponent implements OnInit{
  
  userList : any[]=[];
  users:any;

  showPro: boolean = true;
  showNormal: boolean = false;

  constructor(private http:HttpClient,
              private router:Router){
  }
  ngOnInit(){

  }
  getList(): void {
    this.showPro = true;
    this.showNormal = false;

    const jwt=sessionStorage.getItem('jwt');
    const headers = new HttpHeaders().set('Authorization', 'Bearer '+sessionStorage.getItem('jwt'));
    console.log(headers.get('Authorization'));
    this.http.get<any>('http://localhost:8080/api/find', { headers: headers, observe: 'response' })
      .subscribe(
        (response: HttpResponse<any>) => {
          const userList = response.body.userList;                             
          this.userList=userList;
          console.log(this.userList);
          typeof(userList);
          console.log(typeof(userList[0]));
          
          alert('success');
        },
        (error) => {
          console.log("No authentication", error);
          console.log(headers);
          alert('fail 權限不足');
        }
      );
  }

    getUser(): void{
      this.showPro = false;
      this.showNormal = true;

      const user=sessionStorage.getItem('username');
      const api=`http://localhost:8080/api/find/${user}`
      const headers = new HttpHeaders().set('Authorization', 'Bearer '+sessionStorage.getItem('jwt'));
      console.log(headers.get('Authorization'));
      this.http.post<any>(api,null, { headers: headers, observe: 'response' })
        .subscribe(
          (response: HttpResponse<any>) => {
            const users = response.body.user;                             
            this.users=users;
            console.log(response.body.user.username);
            typeof(users);
            console.log(typeof(users));
            
            alert('success');
          },
          (error) => {
            console.log("No authentication", error);
            console.log(headers);
            alert('fail 權限不足');
          }
        );
      
    }
    logOut(){
      sessionStorage.clear();
      this.router.navigate(["./login"])
    }
}
