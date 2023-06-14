import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {  debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';

@Component({
  selector: 'app-hero-search',
  templateUrl: './hero-search.component.html',
  styleUrls: [ './hero-search.component.css' ]
})
export class HeroSearchComponent implements OnInit {
  heroes$!: Observable<Hero[]>;   //$命名慣例表示一個Observable變數 !非空斷言(告訴ts不用判斷)
  private searchTerms = new Subject<string>(); //Subject可以透過subscribe數據共享 unsubscribe則取消訂閱

  constructor(private heroService: HeroService) {} //注入HeroService

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term); //監聽內容並發出term到serachHeroes
  }

  ngOnInit(): void {
    this.heroes$ = this.searchTerms.pipe(
      
      debounceTime(300), //等待300毫秒避免正在輸入就搜索

      
      distinctUntilChanged(),//忽略重複搜尋

      //switchMap映射一個新的Observable(serachHeroes處理後得到的Observable<Hero[]>)只要訂閱就拿的到這個Observable
      switchMap((term: string) => this.heroService.searchHeroes(term)) 
    );
  }
}

