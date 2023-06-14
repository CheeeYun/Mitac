import { Component , OnInit} from '@angular/core';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
export class HeroesComponent implements OnInit{

  selectedHero?:Hero; //選擇英雄的屬性可以是undefined

  heroes: Hero[]=[];  //英雄屬性列表初始為[]
  
  constructor(private heroService:HeroService){};//注入HeroService

  ngOnInit():void{    //實作OnInit 
    this.getHeroes();
  };

  
  getHeroes():void{   //獲得Observable<Hero[]>透過subscribe將值賦予給heroes
    this.heroService.getHeroes()
    .subscribe(heroes=>this.heroes=heroes);
  }

  add(name:string):void{
    name=name.trim(); //去掉前後空格
    if(!name){return;} //若為空則退出方法     //將name轉型成Hero
    this.heroService.addHero({name}as Hero).subscribe(
      hero=>{this.heroes.push(hero);}
    )
  }
  delete(hero: Hero): void {
    this.heroes = this.heroes.filter(h => h !== hero);//過濾掉不是hero的參數
    this.heroService.deleteHero(hero.id).subscribe();
  }
  




}
