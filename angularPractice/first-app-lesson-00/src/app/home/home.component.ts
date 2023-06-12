import { Component,inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HousingLocationComponent } from '../housing-location/housing-location.component';
import { HousingLocation } from '../housinglocation';
import { HousingService} from '../housing.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule,
            HousingLocationComponent],
  templateUrl: './home.component.html' 
  ,
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  housingLocationList:HousingLocation[]=[]; //初始化拿到HousingLocation類型的[]
  housingService : HousingService = inject(HousingService);  //指派HousingService類給housingLocation變數
  // housingService = inject(HousingService);//?為何要注入自己
  filteredLocationList: HousingLocation[]=[];
  constructor(){    
    this.housingService.getAllHousingLocations().then((housingLocationList: //then catch 類似try&catch
      HousingLocation[])=>{
      this.housingLocationList=housingLocationList;
      this.filteredLocationList =housingLocationList;
    })
  }
  filterResults(text:string){
    if(!text){
      this.filteredLocationList=this.housingLocationList;
    }
    this.filteredLocationList=this.housingLocationList.filter(
      housingLocation=>housingLocation?.city.toLowerCase().includes(text.toLowerCase())
    )
    //housingLocation.city裡包含輸入文字
    //=>過濾條件
    //housingLocation? 問號代表在null/undefined時不會錯誤
  }
}