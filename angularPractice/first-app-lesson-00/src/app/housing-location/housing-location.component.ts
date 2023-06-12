import { Component ,Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import { HousingLocation } from '../housinglocation';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-housing-location',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl : './housing-location.component.html',
  styleUrls: ['./housing-location.component.css']
})
export class HousingLocationComponent {
  @Input()housingLocation!: HousingLocation ;
  @Input()house2:string="1";
}
//!非空斷言(不會為null或為定義)
//typeScript 
//export
//@Input() 讓html使用