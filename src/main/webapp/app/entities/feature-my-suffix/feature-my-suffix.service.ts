import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { FeatureMySuffix } from './feature-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FeatureMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/features';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/features';

    constructor(private http: Http) { }

    create(feature: FeatureMySuffix): Observable<FeatureMySuffix> {
        const copy = this.convert(feature);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(feature: FeatureMySuffix): Observable<FeatureMySuffix> {
        const copy = this.convert(feature);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FeatureMySuffix> {
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
     * Convert a returned JSON object to FeatureMySuffix.
     */
    private convertItemFromServer(json: any): FeatureMySuffix {
        const entity: FeatureMySuffix = Object.assign(new FeatureMySuffix(), json);
        return entity;
    }

    /**
     * Convert a FeatureMySuffix to a JSON which can be sent to the server.
     */
    private convert(feature: FeatureMySuffix): FeatureMySuffix {
        const copy: FeatureMySuffix = Object.assign({}, feature);
        return copy;
    }
}
