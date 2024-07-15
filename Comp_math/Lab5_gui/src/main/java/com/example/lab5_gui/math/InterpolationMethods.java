package com.example.lab5_gui.math;

import java.util.ArrayList;

public class InterpolationMethods {

    // Многочлен Лагранжа
    public static double methodLagrange(double[] rx, double[] ry, double xx) {
//        double l = 0;
//        for (int i = 0; i < rx.length; i++) {
//            double t = 1;
//            for (int j = 0; i < rx.length; i++) {
//                if (j == i) continue;
//                t *= (xx - rx[j]) / (rx[i] - rx[j]);
//            }
//            l += ry[i] * t;
//        }
//        return l;
        return 0;
    }

    // Многочлен Ньютона с разделенными разностями,
    public static double methodNewtonSep(double[] rx, double[] ry, double xx) {
//        int n = rx.length;
//        double[][] diffs = new double[n][];
//        for (int i = 0; i < n; i++) {
//            diffs[i] = (new double[]{ry[i]});
//        }
//
//        for (int k = 1; k < n; k++) {
//            for (int i = 0; i < n - k; i++) {
//                double diff = (diffs[i + 1][k - 1] - diffs[i][k - 1]) / (rx[i + k] - rx[i]);
//                // diffs[i].append(diff)
//            }
//        }
//        double result = diffs[0][0];
//        for (int k = 1; k < n; k++) {
//            double term = diffs[0][k];
//            for (int j = 0; j < k; j++) {
//                term *= (xx - rx[j]);
//            }
//            result += term;
//        }
//        return result;
        return 1;
    }

    public static double methodNewtonEnd(double[] rx, double[] ry, double xx) {

        return 2;
    }

}
