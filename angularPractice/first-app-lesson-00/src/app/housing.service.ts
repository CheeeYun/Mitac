import { Injectable } from '@angular/core';
import { HousingLocation } from './housinglocation';
@Injectable({
  providedIn: 'root'
})
export class HousingService {
  url='http://localhost:3000/locations';
  //async異步可使用await來暫停執行等待一個async完成再繼續執行 
  async getAllHousingLocations() : Promise<HousingLocation[]>{    
    const data=await fetch(this.url); //fetch傳送請求(Get)拿到一個Response給data
    return await data.json()??[]; //Response.json()用來解析json
  }
  async getHousingLocationById(id:number) : Promise<HousingLocation | undefined>{  
    const data=await fetch(`${this.url}/${id}`);
    return await data.json()??{};
  }
  submitApplication(firstName:string, lastName:string, email:string){
    console.log(firstName,lastName,email);
  }
}
