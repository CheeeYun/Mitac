import { Injectable } from '@angular/core';
import { Hero ,EmbeddedData} from './hero';
// import { HEROES } from './mock-heroes';
import { Observable, of} from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError,map,tap} from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class HeroService {
  // private heroesUrl='api/heroes';
  private newUrl='http://localhost:8080/home/heroes'
  private embeddedData:EmbeddedData | null=null;
  httpOptions={       //請求標頭設置為JSON格式
    headers: new HttpHeaders({'Content-Type':'application/json'})
  }

  constructor(
    private http: HttpClient,   
    private messageService:MessageService
    ) { }
  getHeroes():Observable<EmbeddedData> {
    return this.http.get<EmbeddedData>(this.newUrl) //發送get請求返回一個Hero[]
     .pipe( //類似stream() Observable的方法
      tap(_=>this.log('fetched heroes')), //tap執行若成功則打印字串 _代表不使用該參數的值
      catchError(this.handleError<EmbeddedData>('getHeroes'))); //接受兩個參數1錯誤的描述2默認返回值
  }

  getHero(id:number): Observable<Hero>{
    const url=`${this.newUrl}/${id}`;
    return this.http.get<Hero>(url).pipe(
      tap(_=>this.log(`fetched hero id=${id}`)),
      catchError(this.handleError<Hero>(`getHero id${id}`))
    )
  }
  updateHero(hero:Hero): Observable<any>{
    return this.http.put(`${this.newUrl}/${hero.heroId}`,hero,this.httpOptions).pipe(
      tap(_=>this.log(`updated hero id=${hero.id}`)),
      catchError(this.handleError<any>('updateHero'))
    );
  }

  addHero(hero: Hero): Observable<Hero> {
  return this.http.post<Hero>(this.newUrl, hero, this.httpOptions)
  // .pipe(
  //   tap((newHero: Hero) => this.log(`added hero w/ id=${newHero.id}`)),//若成功責打印新id
  //   catchError(this.handleError<Hero>('addHero'))
  // );
}

deleteHero(id: number): Observable<Hero> {
  const url = `${this.newUrl}/${id}`;
  return this.http.delete<Hero>(url, this.httpOptions).pipe(
    tap(_ => this.log(`deleted hero id=${id}`)),
    catchError(this.handleError<Hero>('deleteHero'))
  );
}


searchHeroes(term: string): Observable<Hero[]> {
  if (!term.trim()) { //若去掉空格還是空的 則返回[]
    return of([]);
  }
  return this.http.get<Hero[]>(`${this.newUrl}/search/${term}`).pipe( //return一個Observable<Hero[]>並且call這些API
    tap(x => x.length ?   //判斷有沒有值,如果有>0打印以下
       this.log(`found heroes matching "${term}"`) :
       this.log(`no heroes matching "${term}"`)),
    catchError(this.handleError<Hero[]>('searchHeroes', []))
  );
}

  private log(message:string){  //字定義打印方法
    this.messageService.add(`HeroService:${message}`);
  }
  /**
 * Handle Http operation that failed.
 * Let the app continue.
 *
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 */
private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead

    // TODO: better job of transforming error for user consumption
    this.log(`${operation} failed: ${error.message}`);

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}
}
