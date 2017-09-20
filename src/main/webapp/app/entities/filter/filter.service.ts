import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Filter } from './filter.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FilterService {

    private resourceUrl = SERVER_API_URL + 'api/filters';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(filter: Filter): Observable<Filter> {
        const copy = this.convert(filter);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(filter: Filter): Observable<Filter> {
        const copy = this.convert(filter);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Filter> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.replacedDate = this.dateUtils
            .convertLocalDateFromServer(entity.replacedDate);
    }

    private convert(filter: Filter): Filter {
        const copy: Filter = Object.assign({}, filter);
        copy.replacedDate = this.dateUtils
            .convertLocalDateToServer(filter.replacedDate);
        return copy;
    }
}
