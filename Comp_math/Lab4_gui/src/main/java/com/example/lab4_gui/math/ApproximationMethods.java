package com.example.lab4_gui.math;

import java.util.Arrays;

public class ApproximationMethods {

    // Расчет среднеквадратичного отклонения
    static double calculateMSE(double[] errors) {
        double sum = 0;
        for (double e : errors) {
            sum += e * e;
        }
        return sum / errors.length;
    }

    // Расчет коэффициента корреляции Пирсона
    static double calculatePearsonCorrelation(double[] x, double[] y, double[] coeffs) {
        double meanX = Arrays.stream(x).average().orElse(0);
        double meanY = Arrays.stream(y).average().orElse(0);
        double numerator = 0;
        double denominatorX = 0;
        double denominatorY = 0;

        for (int i = 0; i < x.length; i++) {
            double predictedY = coeffs[0] * x[i] + coeffs[1];
            numerator += (x[i] - meanX) * (y[i] - meanY);
            denominatorX += (x[i] - meanX) * (x[i] - meanX);
            denominatorY += (y[i] - meanY) * (y[i] - meanY);
        }

        return numerator / Math.sqrt(denominatorX * denominatorY);
    }


    // Линейная функция
    public static double[] fitLinear(double[] x, double[] y) {
        double[][] X = new double[x.length][2];
        for (int i = 0; i < x.length; i++) {
            X[i][0] = x[i];
            X[i][1] = 1;
        }

        return solveLeastSquares(X, y);
    }

    // Полиномиальная функция 2-й степени
    public static double[] fitQuadratic(double[] x, double[] y) {
        double[] A = new double[x.length];
        double[] B = new double[x.length];
        double[] C = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            A[i] = x[i] * x[i];
            B[i] = x[i];
            C[i] = 1;
        }

        double[][] X = new double[x.length][3];
        for (int i = 0; i < x.length; i++) {
            X[i][0] = A[i];
            X[i][1] = B[i];
            X[i][2] = C[i];
        }

        double[] coefficients = solveLeastSquares(X, y);
        return coefficients;
    }

    // Полиномиальная функция 3-й степени
    public static double[] fitCubic(double[] x, double[] y) {
        double[] A = new double[x.length];
        double[] B = new double[x.length];
        double[] C = new double[x.length];
        double[] D = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            A[i] = x[i] * x[i] * x[i];
            B[i] = x[i] * x[i];
            C[i] = x[i];
            D[i] = 1;
        }

        double[][] X = new double[x.length][4];
        for (int i = 0; i < x.length; i++) {
            X[i][0] = A[i];
            X[i][1] = B[i];
            X[i][2] = C[i];
            X[i][3] = D[i];
        }

        double[] coefficients = solveLeastSquares(X, y);
        return coefficients;
    }

    // Экспоненциальная функция
    public static double[] fitExponential(double[] x, double[] y) {
        double[] A = new double[x.length];
        double[] B = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            A[i] = Math.exp(x[i]);
            B[i] = 1;
        }

        double[][] X = new double[x.length][2];
        for (int i = 0; i < x.length; i++) {
            X[i][0] = B[i];
            X[i][1] = A[i];
        }

        double[] yLog = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            yLog[i] = Math.log(y[i]);
        }

        double[] coefficients = solveLeastSquares(X, yLog);
        double a = Math.exp(coefficients[0]);
        double b = coefficients[1];
        return new double[]{a, b};
    }

    // Логарифмическая функция
    public static double[] fitLogarithmic(double[] x, double[] y) {
        double[] A = new double[x.length];
        double[] B = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            A[i] = Math.log(x[i]);
            B[i] = 1;
        }

        double[][] X = new double[x.length][2];
        for (int i = 0; i < x.length; i++) {
            X[i][0] = B[i];
            X[i][1] = A[i];
        }

        double[] coefficients = solveLeastSquares(X, y);
        return coefficients;
    }

    // Степенная функция
    public static double[] fitPower(double[] x, double[] y) {
        double[] A = new double[x.length];
        double[] B = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            A[i] = Math.log(x[i]);
            B[i] = 1;
        }

        double[][] X = new double[x.length][2];
        for (int i = 0; i < x.length; i++) {
            X[i][0] = B[i];
            X[i][1] = A[i];
        }

        double[] yLog = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            yLog[i] = Math.log(y[i]);
        }

        double[] coefficients = solveLeastSquares(X, yLog);
        double a = Math.exp(coefficients[0]);
        double b = coefficients[1];
        return new double[]{a, b};
    }

    // Решение системы уравнений методом наименьших квадратов
    private static double[] solveLeastSquares(double[][] X, double[] y) {
        int m = X.length;
        int n = X[0].length;

        double[][] Xt = transpose(X);
        double[][] XtX = multiply(Xt, X);
        double[] XtY = multiply(Xt, y);
        double[] coeff = solveLinearSystem(XtX, XtY);

        return coeff;
    }

    // Транспонирование матрицы
    private static double[][] transpose(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] transposed = new double[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    // Умножение двух матриц
    private static double[][] multiply(double[][] a, double[][] b) {
        int m = a.length;
        int n = b[0].length;
        int k = a[0].length;
        double[][] result = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < k; l++) {
                    result[i][j] += a[i][l] * b[l][j];
                }
            }
        }
        return result;
    }

    // Умножение матрицы на вектор
    private static double[] multiply(double[][] matrix, double[] vector) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[] result = new double[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    // Решение системы линейных уравнений Ax = b
    private static double[] solveLinearSystem(double[][] A, double[] b) {
        int n = A.length;
        double[][] augmentedMatrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][n] = b[i];
        }

        // Прямой ход Гаусса
        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmentedMatrix[k][i]) > Math.abs(augmentedMatrix[maxRow][i])) {
                    maxRow = k;
                }
            }
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[maxRow];
            augmentedMatrix[maxRow] = temp;

            for (int k = i + 1; k < n; k++) {
                double factor = augmentedMatrix[k][i] / augmentedMatrix[i][i];
                for (int j = i; j <= n; j++) {
                    augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                }
            }
        }

        // Обратный ход
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = augmentedMatrix[i][n] / augmentedMatrix[i][i];
            for (int k = i - 1; k >= 0; k--) {
                augmentedMatrix[k][n] -= augmentedMatrix[k][i] * x[i];
            }
        }
        return x;
    }

}
