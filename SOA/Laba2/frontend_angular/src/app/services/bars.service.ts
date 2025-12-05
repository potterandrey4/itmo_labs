import { Injectable } from '@angular/core';
import { HttpClientService, BARS_API_BASE_URL } from './http-client.service';
import { Observable } from 'rxjs';
import { LabWork, Discipline } from '../models';

@Injectable({
    providedIn: 'root'
})
export class BarsService {

    constructor(private httpClient: HttpClientService) { }

    decreaseDifficulty(labworkId: number): Observable<LabWork> {
        return this.httpClient.request<LabWork>(`${BARS_API_BASE_URL}/bars/decrease-difficulty/${labworkId}`, {
            method: 'POST'
        });
    }

    assignDiscipline(labworkId: number, discipline: Discipline): Observable<LabWork> {
        return this.httpClient.request<LabWork>(`${BARS_API_BASE_URL}/bars/assign-discipline/${labworkId}`, {
            method: 'POST',
            body: discipline
        });
    }
}