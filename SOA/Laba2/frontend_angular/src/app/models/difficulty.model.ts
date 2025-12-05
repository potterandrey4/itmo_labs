export type Difficulty = 'VERY_EASY' | 'EASY' | 'INSANE' | 'TERRIBLE';

export const difficultyLabels: Record<Difficulty, string> = {
    VERY_EASY: 'Очень легко',
    EASY: 'Легко',
    INSANE: 'Безумно',
    TERRIBLE: 'Ужасно'
};