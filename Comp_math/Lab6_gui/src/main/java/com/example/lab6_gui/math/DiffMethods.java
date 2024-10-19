package com.example.lab6_gui.math;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class DiffMethods {

	// y(0)=1
    public static double exactSolutionF1(double x) {
        return - x + 2 * Math.exp(x) - 1;
    }

    public static double exactSolutionF2(double x) {
        return (3 * Math.exp(-x) + Math.sin(x) - Math.cos(x)) / 2;
    }

    public static double exactSolutionF3(double x) {
        return 3 * Math.exp(x) - 2 * (x + 1);
    }

    public static double[] euler(BiFunction<Double, Double, Double> f, double y0, double x0, double xn, double h) {
        int n = (int) ((xn - x0) / h);
        double[] x = linespace(x0, xn, n + 1);
        double[] y = new double[n + 1];
        y[0] = y0;

        for (int i = 0; i < n; i++) {
            y[i + 1] = y[i] + h * f.apply(x[i], y[i]);
        }

        return y;
    }

    public static double[] improvedEuler(BiFunction<Double, Double, Double> f, double y0, double x0, double xn, double h) {
        int n = (int) ((xn - x0) / h);
        double[] x = linespace(x0, xn, n + 1);
        double[] y = new double[n + 1];
        y[0] = y0;

        for (int i = 0; i < n; i++) {
            double k1 = f.apply(x[i], y[i]);
            double k2 = f.apply(x[i] + h, y[i] + h * k1);
            y[i + 1] = y[i] + h * (k1 + k2) / 2;
        }

        return y;
    }

    public static double[] adams(BiFunction<Double, Double, Double> f, double y0, double x0, double xn, double h, int m) {
        int n = (int) ((xn - x0) / h);
        double[] x = linespace(x0, xn, n + 1);
        double[] y = new double[n + 1];
        y[0] = y0;

        for (int i = 0; i < m; i++) {
            y[i + 1] = y[i] + h * f.apply(x[i], y[i]);
        }

        for (int i = m; i < n; i++) {
            y[i + 1] = y[i] + h * (55 * f.apply(x[i], y[i]) - 59 * f.apply(x[i - 1], y[i - 1]) + 37 * f.apply(x[i - 2], y[i - 2]) - 9 * f.apply(x[i - 3], y[i - 3])) / 24;
        }

        return y;
    }

    public static double rungeRule(BiFunction<Double, Double, Double> f, double y0, double x0, double xn, double h1, double h2, int p) {
        double[] yH1 = euler(f, y0, x0, xn, h1);
        double[] yH2 = euler(f, y0, x0, xn, h2);

        return Math.abs(yH1[yH1.length - 1] - yH2[yH2.length - 1]) / (Math.pow(2, p) - 1);
    }

    public static double[] linespace(double start, double end, int num) {
        return IntStream.range(0, num).mapToDouble(i -> start + i * (end - start) / (num - 1)).toArray();
    }

}
