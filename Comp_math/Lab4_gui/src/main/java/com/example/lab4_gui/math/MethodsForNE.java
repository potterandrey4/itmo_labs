package com.example.lab4_gui.math;

import com.example.lab4_gui.MethodDataEquation;

import java.util.function.Function;

public class MethodsForNE {

	public static MethodDataEquation bisectionMethod(Function<Double, Double> function, double a, double b, double eps) {
		MethodDataEquation data = new MethodDataEquation("дихотомии");
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
			data.addPoint(mid, function.apply(mid));
		}

		data.setRoot(mid, function.apply(mid));
		data.setIterations(iterations);

		return data;
	}

	public static MethodDataEquation simpleIterationsMethod(Function<Double, Double> function, Function<Double, Double> derivativeFunction, double a, double b, double eps) {
		MethodDataEquation data = new MethodDataEquation("простых итераций");
		double x_prev = (a + b) / 2;  // начальное приближение
		double x_curr;
		int iterations = 0;
		int max_iter = 100;

		// Вычисляем lambda
		double maxDerivative = Math.max(Math.abs(derivativeFunction.apply(a)), Math.abs(derivativeFunction.apply(b)));
		double lambda = 1 / maxDerivative;

		if (derivativeFunction.apply(x_prev) > 0) {
			lambda = -lambda;
		}

		while (true) {
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
			data.addPoint(x_curr, function.apply(x_curr));
			iterations++;
		}

		data.setRoot(x_curr, function.apply(x_curr));
		data.setIterations(iterations);

		return data;
	}

	public static MethodDataEquation newtonMethod(Function<Double, Double> function, Function<Double, Double> derivative, double x0, double eps) {
		MethodDataEquation data = new MethodDataEquation("Ньютона");
		int maxIterations = 100; // Maximum number of iterations for safety

		double x = x0;
		double fx = function.apply(x);
		int iterations = 0;

		while (Math.abs(fx) > eps && iterations < maxIterations) {
			double dfx = derivative.apply(x);
			if (dfx == 0) {
				throw new ArithmeticException("Derivative is zero, method fails.");
			}

			iterations++;
			x = x - fx / dfx;
			fx = function.apply(x);

			data.addPoint(x, fx);
		}

		data.setRoot(x, fx);
		data.setIterations(iterations);

		return data;
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