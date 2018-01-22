import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ImageMySuffix } from './image-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ImageMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/images';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/images';

    constructor(private http: Http) { }

    create(image: ImageMySuffix): Observable<ImageMySuffix> {
        const copy = this.convert(image);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(image: ImageMySuffix): Observable<ImageMySuffix> {
        const copy = this.convert(image);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ImageMySuffix> {
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
     * Convert a returned JSON object to ImageMySuffix.
     */
    private convertItemFromServer(json: any): ImageMySuffix {
        const entity: ImageMySuffix = Object.assign(new ImageMySuffix(), json);
        return entity;
    }

    /**
     * Convert a ImageMySuffix to a JSON which can be sent to the server.
     */
    private convert(image: ImageMySuffix): ImageMySuffix {
        const copy: ImageMySuffix = Object.assign({}, image);
        return copy;
    }
}
