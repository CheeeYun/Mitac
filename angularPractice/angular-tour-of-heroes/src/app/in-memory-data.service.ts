import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Hero } from './hero';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService{
  createDb(){
    const heroes=[
      { id: 12, name: 'Dr. Nice' },
      { id: 13, name: 'Bombasto' },
      { id: 14, name: 'Celeritas' },
      { id: 15, name: 'Magneta' },
      { id: 16, name: 'RubberMan' },
      { id: 17, name: 'Dynama' },
      { id: 18, name: 'Dr. IQ' },
      { id: 19, name: 'Magma' },
      { id: 20, name: 'Tornado' }
    ];
    return {heroes};
  }
  
    genId(heroes:Hero[]):number{ //生成id 接收一個Hero[]返回數字 ...展開符號(取得陣列的值而不是陣列本身)
      return heroes.length>0 ? Math.max(...heroes.map(hero=>hero.id))+1:11;
    }//長度>0找出最大值+1,<0則預設11
}
