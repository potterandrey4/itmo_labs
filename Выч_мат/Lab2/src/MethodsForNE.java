/*
все методы возвращают массив double, где
1й элемент - корень уравнения
2й элемент - значение функциии для этого корня
3й элемент - число итерций
 */

import java.util.function.Function;

public class MethodsForNE {


    public static double[] bisectionMethod(Function<Double, Double> function, double a, double b, double eps) {
        double[] result = new double[3];
        int iterations = 0;
        double mid = (a + b) / 2;

        while (Math.abs(b - a) > eps) {
            mid = (a + b) / 2;
            double fMid = function.apply(mid);

            if (Math.abs(fMid) < eps) {
                break;
            }

            if (function.apply(a) * fMid < 0) {
                b = mid;
            } else {
                a = mid;
            }
            iterations++;
        }

        result[0] = mid;
        result[1] = function.apply(mid);
        result[2] = iterations;

        return result;
    }


    public static double[] secantMethod(Function<Double, Double> function, double a, double b, double eps) {
        double[] result = new double[3];
        double fa = function.apply(a);
        double fb = function.apply(b);
        int iterations = 0;
        double c = 0;

        while (Math.abs(b - a) > eps) {
            c = b - fb * (b - a) / (fb - fa);
            a = b;
            fa = fb;
            b = c;
            fb = function.apply(b);
            iterations++;
        }

        result[0] = c;
        result[1] = function.apply(c);
        result[2] = iterations;

        return result;
    }

    public static double[] newtonMethod(Function<Double, Double> function, Function<Double, Double> derivative, double x0, double eps) {
        double[] result = new double[3];
        int iterations = 0;
        double x = x0;
        double fx = function.apply(x);
        double dfx;

        while (Math.abs(fx) > eps) {
            dfx = derivative.apply(x);
            if (dfx == 0) {
                throw new ArithmeticException("Derivative is zero, method fails.");
            }
            x = x - fx / dfx;
            fx = function.apply(x);
            iterations++;
        }

        result[0] = x;
        result[1] = fx;
        result[2] = iterations;

        return result;
    }

    public static double findInitialApproximation(Function<Double, Double> function, Function<Double, Double> derivative, double a, double b) {
        double fa = function.apply(a);
        double fb = function.apply(b);

        // If function changes sign in the interval, choose the midpoint or a point near zero if applicable
        if (fa * fb < 0) {
            return (a + b) / 2;
        }

        // If a <= 0, and b > 0, start with a small positive value
        if (a <= 0) {
            if (b > 0) {
                return b;
            } else {
                return (a + b) / 2;
            }
        }

        // Otherwise, use the midpoint
        return (a + b) / 2;
    }

}