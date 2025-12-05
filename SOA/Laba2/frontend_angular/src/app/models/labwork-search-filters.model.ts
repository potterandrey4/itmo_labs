import { Difficulty } from './difficulty.model';

export interface LabworkSearchFilters {
    page?: number;
    size?: number;
    sort?: string;
    name?: string;
    minDifficulty?: Difficulty;
    minimalPointGreaterThan?: number;
    personalQualitiesMaximumGreaterThan?: number;
    xGreaterThan?: number;
    xLessThan?: number;
    yGreaterThan?: number;
    yLessThan?: number;
    creationDateFrom?: string;
    creationDateTo?: string;
    disciplineName?: string;
}