import { Difficulty } from './difficulty.model';
import { Coordinates } from './coordinates.model';

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

export { Difficulty };
