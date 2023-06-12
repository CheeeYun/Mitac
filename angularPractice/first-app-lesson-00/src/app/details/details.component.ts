import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HousingService } from '../housing.service';
import { HousingLocation } from '../housinglocation';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-details',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: `./details.components.html`,
  styleUrls: ['./details.component.css']
  
})
export class DetailsComponent {
  // route: ActivatedRoute= inject(ActivatedRoute); 
  route= inject(ActivatedRoute); 
  housingService=new HousingService //?housingService=inject(HousingService)
  housingLocation: HousingLocation | undefined; //如果不是資料型態
  applyForm=new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    email: new FormControl('') //('')默認空字串
  });
  constructor(){            //route.snapshot.params可以獲取名為id的參數 10代表十進位整數
    const housingLocationId= parseInt(this.route.snapshot.params['id'],10);
    this.housingService.getHousingLocationById(housingLocationId)
    .then(housingLocation=>{ this.housingLocation=housingLocation;});
  }
  submitApplication(){  //?? ''弱勢null/undefined 默認為空字串
    this.housingService.submitApplication(
      this.applyForm.value.firstName??'',
      this.applyForm.value.lastName??'',
      this.applyForm.value.email??''
    )
  }

}
