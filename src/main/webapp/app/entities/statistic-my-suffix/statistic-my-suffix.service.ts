import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { StatisticMySuffix } from './statistic-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StatisticMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/statistics';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/statistics';

    constructor(private http: Http) { }

    create(statistic: StatisticMySuffix): Observable<StatisticMySuffix> {
        const copy = this.convert(statistic);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(statistic: StatisticMySuffix): Observable<StatisticMySuffix> {
        const copy = this.convert(statistic);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<StatisticMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to StatisticMySuffix.
     */
    private convertItemFromServer(json: any): StatisticMySuffix {
        const entity: StatisticMySuffix = Object.assign(new StatisticMySuffix(), json);
        return entity;
    }

    /**
     * Convert a StatisticMySuffix to a JSON which can be sent to the server.
     */
    private convert(statistic: StatisticMySuffix): StatisticMySuffix {
        const copy: StatisticMySuffix = Object.assign({}, statistic);
        return copy;
    }
}
