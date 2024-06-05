/*
все методы возвращают массив double, где
1й элемент - корень уравнения
2й элемент - значение функциии для этого корня
3й элемент - число итерций
 */

import java.util.function.Function;

public class MethodsForNE {

    public static double[] bisectionMethod(Function<Double, Double> function, double a, double b, double eps) {
        double c;
        int iterations = 0;
        while (function.apply((b - a)/2) > eps) {
            iterations++;
            c = (a + b) / 2;
            if (function.apply(c) * function.apply(c) < 0) {
                b = c;
            } else {
                a = c;
            }
        }
        return new double[]{(a + b) / 2, function.apply((a + b) / 2), iterations};
    }


    public static double[] chordMethod(Function<Double, Double> function, double a, double b, double eps) {
        double x, y;
        double x0, x1;
        int iterations = 0;

        while (b - a > eps) {
            iterations++;

            x0 = a;
            x1 = b;

            y = function.apply(x1) - function.apply(x0);
            x = x1 - (function.apply(x1) * (x1 - x0)) / y;

            if (Math.abs(function.apply(x)) <= eps) {
                return new double[]{x, function.apply(x), iterations};
            } else if (function.apply(a) * function.apply(x) < 0) {
                b = x;
            } else {
                a = x;
            }
        }

        return new double[]{(a + b) / 2, function.apply((a + b) / 2), iterations};
    }

    public static double[] newtonMethod(Function<Double, Double> function, Function<Double, Double> derivativeFunction, double x0, double eps) {
        double x = x0;
        int iterations = 0;
        while (Math.abs(function.apply(x)) > eps && iterations <= 100) {
            iterations++;

            x = x - function.apply(x) / derivativeFunction.apply(x);

            if (Math.abs(function.apply(x)) <= eps) {
                return new double[]{x, function.apply(x), iterations};
            }
        }

        return new double[]{x, function.apply(x), iterations};
    }

    public static double findInitialApproximation(Function<Double, Double> function, Function<Double, Double> derivativeDerivativeFunction, double a, double b) {

        double f_a = function.apply(a);
        double ddf_a = derivativeDerivativeFunction.apply(a);

        if (f_a * ddf_a > 0) {
            return a;
        }
        else {
            return b;
        }
    }

}