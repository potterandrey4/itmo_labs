import { Difficulty } from './difficulty.model';
import { Coordinates } from './coordinates.model';

export interface LabWorkInput {
    name: string;
    coordinates: Coordinates;
    minimalPoint: string | null;
    personalQualitiesMaximum?: string | null;
    difficulty?: Difficulty | null;
    disciplineId: number | null;
}