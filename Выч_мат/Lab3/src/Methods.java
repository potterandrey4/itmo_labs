public class Methods {
	public static boolean checkConvergence(int s, double a, double b) {
		if (s == 3) {
			if ((a <= 0 && b >= 0)) {
				System.out.println("Интеграл расходится.");
				System.exit(0);
			}
		} else if (s == 4) {
			if (a < -1 || b < -1) {
				System.out.println("Интеграл расходится.");
				System.exit(0);
			}
		}
		return true;
	}

	// точное вычисление интеграла
	public static double exactIntegral(IntegralsEnum integ, double a, double b) {
		return integ.calculate(b) - integ.calculate(a);
	}

	public static double runge(double currAns, double prevAns, int order) {
		return Math.abs(currAns - prevAns) / (Math.pow(order, 2) - 1);
	}

	public static double midRectangleMethod(FunctionsEnum func, double a, double b, int n, double e) {
		double currAns, prevAns = 0;
		int iterations = 0;
		do {
			double step = (b - a) / n;
			double sumVal = 0;
			for (int i = 0; i < n; i++) {
				double xMid = a + (i + 0.5) * step;
				sumVal += func.calculate(xMid);
			}
			currAns = step * sumVal;
			if (iterations > 0) {
				double error = runge(currAns, prevAns, 2);
				if (error <= e) break;
			}
			prevAns = currAns;
			n *= 2;
			iterations++;
		} while (iterations <= 10000000);
		return currAns;	}

	public static double rightRectangleMethod(FunctionsEnum func, double a, double b, int n, double e) {
		double currAns, prevAns = 0;
		int iterations = 0;
		do {
			double step = (b - a) / n;
			double integral = 0;
			for (int i = 0; i < n; i++) {
				double xRight = a + (i + 1) * step;
				integral += func.calculate(xRight);
			}
			currAns = step * integral;
			if (iterations > 0) {
				double error = runge(currAns, prevAns, 2);
				if (error <= e) break;
			}
			prevAns = currAns;
			n *= 2;
			iterations++;
		} while (iterations <= 10000000);
		return currAns;
	}

	public static double leftRectangleMethod(FunctionsEnum func, double a, double b, int n, double e) {
		double currAns, prevAns = 0;
		int iterations = 0;
		do {
			double step = (b - a) / n;
			double integral = 0;
			for (int i = 0; i < n; i++) {
				double xLeft = a + i * step;
				integral += func.calculate(xLeft);
			}
			currAns = step * integral;
			if (iterations > 0) {
				double error = runge(currAns, prevAns, 2);
				if (error <= e) break;
			}
			prevAns = currAns;
			n *= 2;
			iterations++;
		} while (iterations <= 10000000);
		return currAns;

	}

	public static double trapezoidalMethod(FunctionsEnum func, double a, double b, int n, double e) {
		double currAns, prevAns = 0;
		int iterations = 0;
		do {
			double step = (b - a) / n;
			double integral = (func.calculate(a) + func.calculate(b)) / 2;
			for (int i = 1; i < n; i++) {
				double x = a + i * step;
				integral += func.calculate(x);
			}
			currAns = step * integral;
			if (iterations > 0) {
				double error = runge(currAns, prevAns, 2);
				if (error <= e) break;
			}
			prevAns = currAns;
			n *= 2;
			iterations++;
		} while (iterations <= 10000000);
		return currAns;
	}

	public static double simpsonMethod(FunctionsEnum func, double a, double b, int n, double e) {
		double currAns, prevAns = 0;
		int iterations = 0;
		do {
			double step = (b - a) / n;
			double integral = func.calculate(a) + func.calculate(b);
			for (int i = 1; i < n; i++) {
				double x = a + i * step;
				if (i % 2 == 0) {
					integral += 2 * func.calculate(x);
				} else {
					integral += 4 * func.calculate(x);
				}
			}
			currAns = step / 3 * integral;
			if (iterations > 0) {
				double error = runge(currAns, prevAns, 4);
				if (error <= e) break;
			}
			prevAns = currAns;
			n *= 2;
			iterations++;
		} while (iterations <= 10000000);
		return currAns;
	}

	public static double calculateIntegral(CustomFunction method, FunctionsEnum func, double a, double b, double e, int n) {
		double currAns = 0, prevAns = 0;
		while (true) {
			currAns = method.apply(func, a, b, n, e);
			if (n > 4) {
				double r = Math.abs(currAns - prevAns) / 3;
				if (r <= e) {
					break;
				}
			}
			prevAns = currAns;
			n *= 2;
			if (n > 10000000) {
				break;
			}
		}
		return currAns;
	}

}
