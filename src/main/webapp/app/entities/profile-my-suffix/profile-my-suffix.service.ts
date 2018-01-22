import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ProfileMySuffix } from './profile-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProfileMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/profiles';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/profiles';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(profile: ProfileMySuffix): Observable<ProfileMySuffix> {
        const copy = this.convert(profile);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(profile: ProfileMySuffix): Observable<ProfileMySuffix> {
        const copy = this.convert(profile);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ProfileMySuffix> {
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
     * Convert a returned JSON object to ProfileMySuffix.
     */
    private convertItemFromServer(json: any): ProfileMySuffix {
        const entity: ProfileMySuffix = Object.assign(new ProfileMySuffix(), json);
        entity.createdDate = this.dateUtils
            .convertDateTimeFromServer(json.createdDate);
        entity.lastUpdatedDate = this.dateUtils
            .convertDateTimeFromServer(json.lastUpdatedDate);
        return entity;
    }

    /**
     * Convert a ProfileMySuffix to a JSON which can be sent to the server.
     */
    private convert(profile: ProfileMySuffix): ProfileMySuffix {
        const copy: ProfileMySuffix = Object.assign({}, profile);

        copy.createdDate = this.dateUtils.toDate(profile.createdDate);

        copy.lastUpdatedDate = this.dateUtils.toDate(profile.lastUpdatedDate);
        return copy;
    }
}
