import { Routes} from '@angular/router';
import { HomeComponent } from './home/home.component';
import { DetailsComponent } from './details/details.component';

const routeConfig : Routes= [  //const 常數(不能更改)
   { path: '',
    component: HomeComponent,
    title: 'Home page'},
   {
     path: 'details/:id', //路徑是變數
     component: DetailsComponent,
     title: 'Home details',
   }
]
export default routeConfig; //export(將routeConfig導出)
//加上default 在import時不用加上{} ex:import routeConfig form './app/routes';