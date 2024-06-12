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

	public static double exactIntegral(IntegralsEnum integ, double a, double b) {
		return integ.calculate(b) - integ.calculate(a);
	}

	public static double runge(double currAns, double prevAns, int order) {
		return Math.abs(currAns - prevAns) / (Math.pow(order, 2) - 1);
	}

	public static double midpointRule(FunctionsEnum func, double a, double b, int n) {
		double step = (b - a) / n;
		double sumVal = 0;
		for (int i = 0; i < n; i++) {
			double xMid = a + (i + 0.5) * step;
			sumVal += func.calculate(xMid);
		}
		return step * sumVal;
	}

	public static Double midpointRule(Double aDouble) {
		return 0d;
	}

	public static double rightRectangleRule(FunctionsEnum func, double a, double b, int n) {
		double step = (b - a) / n;
		double integral = 0;
		for (int i = 0; i < n; i++) {
			double xRight = a + (i + 1) * step;
			integral += func.calculate(xRight);
		}
		return step * integral;
	}

	public static double leftRectangleRule(FunctionsEnum func, double a, double b, int n) {
		double step = (b - a) / n;
		double integral = 0;
		for (int i = 0; i < n; i++) {
			double xLeft = a + i * step;
			integral += func.calculate(xLeft);
		}
		return step * integral;
	}

	public static double trapezoidalRule(FunctionsEnum func, double a, double b, int n) {
		double step = (b - a) / n;
		double integral = (func.calculate(a) + func.calculate(b)) / 2;
		for (int i = 1; i < n; i++) {
			double x = a + i * step;
			integral += func.calculate(x);
		}
		return step * integral;
	}

	public static double simpsonRule(FunctionsEnum func, double a, double b, int n) {
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
		return step / 3 * integral;
	}

	public static double calculateIntegral(CustomFunction method, FunctionsEnum func, double a, double b, double e, int n) {
		double currAns = 0, prevAns = 0;
		while (true) {
			currAns = method.apply(func, a, b, n);
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
