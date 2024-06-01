/*
все методы возвращают массив double, где
1й элемент - корень уравнения
2й элемент - значение функциии для этого корня
3й элемент - число итерций
 */

import java.util.function.Function;

public class MethodsForNE {

    public static double[] bisectionMethod(Function<Double, Double> function, double a, double b, double eps) {
        double x0, x1;
        int iterations = 0;

        while (b - a > eps) {
            x0 = (a + b) / 2;
            x1 = function.apply(x0);

            if (Math.abs(x1) < eps) {
                return new double[]{x0, x1, iterations};
            } else if (function.apply(a) * x1 < 0) {
                b = x0;
            } else {
                a = x0;
            }

            iterations++;
        }

        return new double[]{(a + b) / 2, function.apply((a + b) / 2), iterations};
    }


    public static double[] chordMethod(Function<Double, Double> function, double a, double b, double eps) {
        double x, y;
        double x0, x1;
        int iterations = 0;

        while (b - a > eps) {
            x0 = a;
            x1 = b;

            y = function.apply(x1) - function.apply(x0);
            x = x1 - (function.apply(x1) * (x1 - x0)) / y;

            if (Math.abs(function.apply(x)) < eps) {
                return new double[]{x, function.apply(x), iterations};
            } else if (function.apply(a) * function.apply(x) < 0) {
                b = x;
            } else {
                a = x;
            }

            iterations++;
        }

        return new double[]{(a + b) / 2, function.apply((a + b) / 2), iterations};
    }


}
