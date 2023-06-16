export interface Hero{
    id: number;
    heroId:number;
    name: string;
    status: Status;
    _links:{
        self:{
            href:string;
        },
        heroes:{
            href:string;
        }
    };
}
export interface EmbeddedData{
    _embedded:{
        heroList:Hero[];
    };
}
export enum Status{
    AVAILABLE  ,
	CANCEL
}