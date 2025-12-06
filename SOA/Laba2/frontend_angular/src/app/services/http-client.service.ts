import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ErrorPayload } from '../models';

export const LABWORK_API_BASE_URL = 'https://77.83.86.104:8443/LabworkService-1';
export const BARS_API_BASE_URL = 'https://77.83.86.104:8444';

@Injectable({
    providedIn: 'root'
})
export class HttpClientService {

    constructor(private http: HttpClient) { }

    request<T>(url: string, options?: {
        method?: string;
        body?: any;
        headers?: HttpHeaders;
    }): Observable<T> {
        const headers = options?.headers || new HttpHeaders();
        if (!headers.has('Accept')) {
            headers.set('Accept', 'application/json');
        }
        if (options?.body && !headers.has('Content-Type')) {
            headers.set('Content-Type', 'application/json');
        }

        const httpOptions = {
            headers,
            body: options?.body
        };

        let obs: Observable<any>;
        switch (options?.method?.toUpperCase()) {
            case 'POST':
                obs = this.http.post(url, options.body, httpOptions);
                break;
            case 'PUT':
                obs = this.http.put(url, options.body, httpOptions);
                break;
            case 'DELETE':
                obs = this.http.delete(url, httpOptions);
                break;
            default:
                obs = this.http.get(url, httpOptions);
        }

        return obs.pipe(
            map(response => response as T),
            catchError(error => {
                let payload: ErrorPayload | undefined;
                if (error.error) {
                    payload = error.error;
                }
                return throwError(new ApiError(error.status, payload, error.message));
            })
        );
    }

    buildQuery(params: Record<string, string | number | undefined | null>): string {
        const entries = Object.entries(params).filter(([, value]) => value !== undefined && value !== null && value !== '');
        if (!entries.length) {
            return '';
        }
        let query = new HttpParams();
        entries.forEach(([key, value]) => {
            query = query.append(key, String(value));
        });
        return `?${query.toString()}`;
    }
}

export class ApiError extends Error {
    status: number;
    payload?: ErrorPayload;

    constructor(status: number, payload?: ErrorPayload, message?: string) {
        super(message ?? payload?.message ?? `Request failed with status ${status}`);
        this.status = status;
        this.payload = payload;
    }
}