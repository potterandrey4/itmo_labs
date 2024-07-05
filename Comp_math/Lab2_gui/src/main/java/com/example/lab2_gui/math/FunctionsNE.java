package com.example.lab2_gui.math;

public class FunctionsNE {

	public static double function1(double x) {
		return Math.pow(x, 3) - 2.561 * Math.pow(x, 2) - 1.325 * x + 4.395;
	}

	public static double derivativeFunction1(double x) {
		return 3 * Math.pow(x, 2) - 5.122 * x - 1.325;
	}

	public static double derivativeDerivativeFunction1(double x) {
		return 6 * x - 5.122;
	}

	public static double function2(double x) {
		return -1.38 * Math.pow(x, 3) - 5.42 * Math.pow(x, 2) + 2.57 * x + 10.95;
	}

	public static double derivativeFunction2(double x) {
		return -4.14 * Math.pow(x, 2) - 10.84 * x + 2.57;
	}

	public static double derivativeDerivativeFunction2(double x) {
		return -8.28 * x - 10.84;
	}

	public static double function3(double x) {
		return Math.pow(Math.sin(x), 3);
	}

	public static double derivativeFunction3(double x) {
		return Math.pow(Math.sin(x), 2);
	}

	public static double derivativeDerivativeFunction3(double x) {
		return Math.sin(x);
	}

	public static double function4(double x) {
		return Math.abs(Math.pow(x, 3) - 3 * x);
	}

	public static double derivativeFunction4(double x) {
		if (x >= 0) {
			return 3 * Math.pow(x, 2);
		} else {
			return -3 * Math.pow(x, 2);
		}
	}

	public static double derivativeDerivativeFunction4(double x) {
		return 6 * x;
	}

}
