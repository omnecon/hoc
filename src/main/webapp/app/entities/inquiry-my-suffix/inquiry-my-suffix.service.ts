import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { InquiryMySuffix } from './inquiry-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InquiryMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/inquiries';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/inquiries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(inquiry: InquiryMySuffix): Observable<InquiryMySuffix> {
        const copy = this.convert(inquiry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(inquiry: InquiryMySuffix): Observable<InquiryMySuffix> {
        const copy = this.convert(inquiry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<InquiryMySuffix> {
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
     * Convert a returned JSON object to InquiryMySuffix.
     */
    private convertItemFromServer(json: any): InquiryMySuffix {
        const entity: InquiryMySuffix = Object.assign(new InquiryMySuffix(), json);
        entity.createdDate = this.dateUtils
            .convertDateTimeFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a InquiryMySuffix to a JSON which can be sent to the server.
     */
    private convert(inquiry: InquiryMySuffix): InquiryMySuffix {
        const copy: InquiryMySuffix = Object.assign({}, inquiry);

        copy.createdDate = this.dateUtils.toDate(inquiry.createdDate);
        return copy;
    }
}
