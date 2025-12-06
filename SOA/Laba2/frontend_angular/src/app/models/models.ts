export type Difficulty = 'VERY_EASY' | 'EASY' | 'INSANE' | 'TERRIBLE';

export const difficultyLabels: Record<Difficulty, string> = {
    VERY_EASY: 'Очень легко',
    EASY: 'Легко',
    INSANE: 'Безумно',
    TERRIBLE: 'Ужасно'
};

export interface Coordinates {
    x: string | null;
    y: string | null;
}

export interface Discipline {
    id: number;
    name: string;
    practiceHours?: number | null;
}

export interface LabWork {
    id: number;
    name: string;
    coordinates: Coordinates;
    creationDate: string;
    minimalPoint: string;
    personalQualitiesMaximum?: string | null;
    difficulty?: Difficulty | null;
    disciplineId: number;
}

export interface LabWorkInput {
    name: string;
    coordinates: Coordinates;
    minimalPoint: string | null;
    personalQualitiesMaximum?: string | null;
    difficulty?: Difficulty | null;
    disciplineId: number;
}

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

export interface DisciplineInput {
    name: string;
    practiceHours?: number | null;
}

export interface ErrorPayload {
    timestamp?: string;
    status?: number;
    error?: string;
    message?: string;
    path?: string;
}

export interface LabworkSearchFilters {
    page?: number;
    size?: number;
    sort?: string;
    name?: string;
    minDifficulty?: Difficulty;
    minimalPointGreaterThan?: number;
    minimalPointLessThan?: number;
    personalQualitiesMaximumGreaterThan?: number;
    personalQualitiesMaximumLessThan?: number;
    xGreaterThan?: number;
    xLessThan?: number;
    yGreaterThan?: number;
    yLessThan?: number;
    creationDateFrom?: string;
    creationDateTo?: string;
    disciplineName?: string;
}