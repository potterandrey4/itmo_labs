import { Injectable } from '@angular/core';
import { HttpClientService, LABWORK_API_BASE_URL } from './http-client.service';
import { Observable } from 'rxjs';
import { LabWork, LabWorkInput, LabworkSearchFilters, PageLabWork, Discipline, DisciplineInput } from '../models';

@Injectable({
    providedIn: 'root'
})
export class LabworkService {

    constructor(private httpClient: HttpClientService) { }

    searchLabworks(filters: LabworkSearchFilters): Observable<PageLabWork> {
        const query = this.httpClient.buildQuery(filters as Record<string, string | number | undefined | null>);
        return this.httpClient.request<PageLabWork>(`${LABWORK_API_BASE_URL}/labworks/search${query}`, {
            method: 'POST'
        });
    }

    createLabwork(payload: LabWorkInput): Observable<LabWork> {
        return this.httpClient.request<LabWork>(`${LABWORK_API_BASE_URL}/labworks`, {
            method: 'POST',
            body: payload
        });
    }

    updateLabwork(id: number, payload: LabWorkInput): Observable<LabWork> {
        return this.httpClient.request<LabWork>(`${LABWORK_API_BASE_URL}/labworks/${id}`, {
            method: 'PUT',
            body: payload
        });
    }

    deleteLabwork(id: number): Observable<void> {
        return this.httpClient.request<void>(`${LABWORK_API_BASE_URL}/labworks/${id}`, {
            method: 'DELETE'
        });
    }

    getGroupedByDiscipline(): Observable<Record<string, number>> {
        return this.httpClient.request<Record<string, number>>(`${LABWORK_API_BASE_URL}/labworks/by/discipline-groups`);
    }

    getByPrefixedNames(prefix: string): Observable<LabWork[]> {
        const query = this.httpClient.buildQuery({ prefix });
        return this.httpClient.request<LabWork[]>(`${LABWORK_API_BASE_URL}/labworks/by/prefixed-names${query}`);
    }

    getByDifficultyGreaterThan(difficulty: string): Observable<LabWork[]> {
        const query = this.httpClient.buildQuery({ difficulty });
        return this.httpClient.request<LabWork[]>(`${LABWORK_API_BASE_URL}/labworks/by/difficulty-greater-than${query}`);
    }

    getLabwork(id: number): Observable<LabWork> {
        return this.httpClient.request<LabWork>(`${LABWORK_API_BASE_URL}/labworks/${id}`);
    }

    // Discipline functions
    getAllDisciplines(): Observable<Discipline[]> {
        return this.httpClient.request<Discipline[]>(`${LABWORK_API_BASE_URL}/disciplines`);
    }

    getDiscipline(id: number): Observable<Discipline> {
        return this.httpClient.request<Discipline>(`${LABWORK_API_BASE_URL}/disciplines/${id}`);
    }

    createDiscipline(payload: DisciplineInput): Observable<Discipline> {
        return this.httpClient.request<Discipline>(`${LABWORK_API_BASE_URL}/disciplines`, {
            method: 'POST',
            body: payload
        });
    }

    updateDiscipline(id: number, payload: DisciplineInput): Observable<Discipline> {
        return this.httpClient.request<Discipline>(`${LABWORK_API_BASE_URL}/disciplines/${id}`, {
            method: 'PUT',
            body: payload
        });
    }

    deleteDiscipline(id: number): Observable<void> {
        return this.httpClient.request<void>(`${LABWORK_API_BASE_URL}/disciplines/${id}`, {
            method: 'DELETE'
        });
    }
}