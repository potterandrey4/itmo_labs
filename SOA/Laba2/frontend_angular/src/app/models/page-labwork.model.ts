import { LabWork } from './labwork.model';

export interface PageLabWork {
    content: LabWork[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    numberOfElements: number;
    first: boolean;
    last: boolean;
    empty: boolean;
}