package org.example;

public class ArcCosCalculator {

    private static final int DEFAULT_ITERATIONS = 100;
    private static final double ACCURACY = 1e-10;

    /**
     * Вычисляет arccos(x) с помощью разложения в ряд.
     * Формула: arccos(x) = π/2 - arcsin(x), где
     * arcsin(x) = x + (1/2)(x^3/3) + (1·3)/(2·4)(x^5/5) + (1·3·5)/(2·4·6)(x^7/7) +
     * ...
     * Ряд сходится при |x| < 1
     * 
     * @param x аргумент в диапазоне [-1, 1]
     * @return значение arccos(x) в радианах
     * @throws IllegalArgumentException если x выходит за пределы [-1, 1]
     */
    public double calculate(double x) {
        return calculate(x, DEFAULT_ITERATIONS);
    }

    /**
     * Вычисляет arccos(x) с заданным числом итераций.
     *
     * @param x          аргумент в диапазоне [-1, 1]
     * @param iterations количество членов ряда для вычисления
     * @return значение arccos(x) в радианах
     * @throws IllegalArgumentException если x выходит за пределы [-1, 1]
     */
    public double calculate(double x, int iterations) {
        // Проверка входных данных
        if (x < -1 || x > 1) {
            throw new IllegalArgumentException("Аргумент должен быть в диапазоне [-1, 1]");
        }

        // Особые случаи
        if (Math.abs(x - 1) < ACCURACY) {
            return 0.0;
        }
        if (Math.abs(x + 1) < ACCURACY) {
            return Math.PI;
        }
        if (Math.abs(x) < ACCURACY) {
            return Math.PI / 2;
        }

        // Вычисляем arcsin(x) через ряд Тейлора
        double arcsin = x;
        double term = x;

        for (int i = 1; i < iterations; i++) {
            double coef = (2 * i - 1.0) / (2 * i);
            term *= x * x * coef / (2 * i + 1);
            arcsin += term;

            if (Math.abs(term) < ACCURACY) {
                break;
            }
        }

        // arccos(x) = π/2 - arcsin(x)
        return Math.PI / 2 - arcsin;
    }
}
