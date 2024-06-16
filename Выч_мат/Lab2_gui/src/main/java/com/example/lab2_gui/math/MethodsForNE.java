package com.example.lab2_gui.math;

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

	private static double phi(Function<Double, Double> function, Function<Double, Double> deritiveFunction, double x, double a, double b, double sign) {
		double lambda = sign * Math.max(Math.abs(deritiveFunction.apply(a)), Math.abs(deritiveFunction.apply(b)));
		return x + lambda * function.apply(x);
	}

	public static double[] simpleIterationsMethod(Function<Double, Double> function, Function<Double, Double> derivativeFunction, double a, double b, double eps) {
		double[] result = new double[3];
		double x_prev = (a + b) / 2;  // начальное приближение
		double x_curr;
		int iterations = 0;
		int max_iter = 100;

		// Вычисляем lambda
//		double lambda = -1.0 / Math.max(Math.abs(derivativeFunction.apply(a)), Math.abs(derivativeFunction.apply(b)));
		double maxDerivative = Math.max(Math.abs(derivativeFunction.apply(a)), Math.abs(derivativeFunction.apply(b)));
		double lambda = 1 / maxDerivative;

		if (derivativeFunction.apply(x_prev) > 0) {
			lambda = -lambda;
		}

		Function<Double, Double> phi = (x) -> x + 1 / 23.005 * function.apply(x);

		while (true) {
			// Определяем функцию φ(x)
			x_curr = x_prev + lambda * function.apply(x_prev);

			// Проверяем условие выхода
			if (Math.abs(x_curr - x_prev) <= eps || iterations >= max_iter) {
				break;
			}

			// Проверка на бесконечные и NaN значения
			if (Double.isNaN(x_prev) || Double.isInfinite(x_prev)) {
				System.out.println("Warning: function returned NaN or Infinite value.");
				break;
			}

			x_prev = x_curr;
			iterations++;
		}

		result[0] = x_curr; // найденное решение
		result[1] = function.apply(x_curr); // значение функции в найденной точке
		result[2] = iterations; // количество итераций

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