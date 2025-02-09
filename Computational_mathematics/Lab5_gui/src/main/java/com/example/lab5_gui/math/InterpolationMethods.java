package com.example.lab5_gui.math;

public class InterpolationMethods {

    // Многочлен Лагранжа
    public static double methodLagrange(double[] rx, double[] ry, double xx) {
        double l = 0;
        for (int i = 0; i < rx.length; i++) {
            double t = 1;
            for (int j = 0; j < rx.length; j++) {
                if (j == i) continue;
                t *= (xx - rx[j]) / (rx[i] - rx[j]);
            }
            l += ry[i] * t;
        }
        return l;
    }

    // Многочлен Ньютона с разделенными разностями,
    public static double methodNewtonSep(double[] rx, double[] ry, double xx) {
        int n = rx.length;
        double[][] diffs = new double[n][n];
        for (int i = 0; i < n; i++) {
            diffs[i][0] = ry[i];
        }
        for (int k = 1; k < n; k++) {
            for (int i = 0; i < n - k; i++) {
                diffs[i][k] = (diffs[i + 1][k - 1] - diffs[i][k - 1]) / (rx[i + k] - rx[i]);
            }
        }
        double result = diffs[0][0];
        for (int k = 1; k < n; k++) {
            double term = diffs[0][k];
            for (int j = 0; j < k; j++) {
                term *= (xx - rx[j]);
            }
            result += term;
        }
        return result;
    }

	// Многочлен Ньютона с конечными разностями
	public static double methodNewtonEnd(double[] rx, double[] ry, double xx) {
		int n = rx.length;
		double h = rx[1] - rx[0]; // Предполагаем, что узлы равноотстоящие
		double[] diff = new double[n];

		// Инициализация конечных разностей
		for (int i = 0; i < n; i++) {
			diff[i] = ry[i];
		}

		// Вычисляем конечные разности
		for (int k = 1; k < n; k++) {
			for (int i = 0; i < n - k; i++) {
				diff[i] = (diff[i + 1] - diff[i]) / (rx[i + k] - rx[i]);
			}
		}

		double result;
		if (xx <= rx[n / 2]) {
			// Первая интерполяционная формула Ньютона
			int x0 = n - 1;
			for (int i = 0; i < n; i++) {
				if (xx <= rx[i]) {
					x0 = i - 1;
					break;
				}
			}
			if (x0 < 0) x0 = 0;
			double t = (xx - rx[x0]) / h;

			result = ry[x0];
			for (int i = 1; i < n; i++) {
				result += (t_calc(t, i, true) * diff[x0]) / factorial(i);
			}
		} else {
			// Вторая интерполяционная формула Ньютона
			double t = (xx - rx[n - 1]) / h;

			result = ry[n - 1];
			for (int i = 1; i < n; i++) {
				result += (t_calc(t, i, false) * diff[n - i - 1]) / factorial(i);
			}
		}

		return result;
	}

	// Вычислить параметр 't' для многочлена Ньютона
	private static double t_calc(double t, int n, boolean forward) {
		double result = t;
		for (int i = 1; i < n; i++) {
			if (forward) {
				result *= t - i;
			} else {
				result *= t + i;
			}
		}
		return result;
	}

	// вычисление факториала
	private static int factorial(int n) {
		int fact = 1;
		for (int i = 2; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}

}
