import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Pool } from './pool.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PoolService {

    private resourceUrl = SERVER_API_URL + 'api/pools';

    constructor(private http: Http) { }

    create(pool: Pool): Observable<Pool> {
        const copy = this.convert(pool);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pool: Pool): Observable<Pool> {
        const copy = this.convert(pool);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Pool> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByCustomer(id: number): Observable<Pool[]> {
        return this.http.get(`${this.resourceUrl}/customer/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    logThis(s: string) {
        this.http.get(`${this.resourceUrl}/log/${s}`);
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(pool: Pool): Pool {
        const copy: Pool = Object.assign({}, pool);
        return copy;
    }
}
