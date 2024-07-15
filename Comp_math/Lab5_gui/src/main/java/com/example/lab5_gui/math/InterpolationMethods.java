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

    public static double methodNewtonEnd(double[] rx, double[] ry, double xx) {

        return 2;
    }

}
