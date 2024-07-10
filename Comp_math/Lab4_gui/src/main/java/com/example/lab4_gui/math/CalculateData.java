package com.example.lab4_gui.math;

import com.example.lab4_gui.DataBean;

import static com.example.lab4_gui.math.ApproximationMethods.calculateMSE;
import static com.example.lab4_gui.math.ApproximationMethods.calculatePearsonCorrelation;

public class CalculateData {
    static double[] mse = new double[6];
    static String[] functionNames = {
            "Линейная аппроксимация",
            "Квадратичная аппроксимация",
            "Полиномиальная аппроксимация 3-й степени",
            "Экспоненциальная аппроксимация",
            "Логарифмическая аппроксимация",
            "Степенная аппроксимация"
    };

    public static DataBean apply(double[] x, double[] y) {
        DataBean result = new DataBean();

        // Линейная аппроксимация
        double[] linearCoeffs = ApproximationMethods.fitLinear(x, y);
        double[] yLinear = new double[x.length];
        double[] eLinear = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            yLinear[i] = linearCoeffs[0] * x[i] + linearCoeffs[1];
            eLinear[i] = y[i] - yLinear[i];
        }
        mse[0] = calculateMSE(eLinear);
        double rPearson = calculatePearsonCorrelation(x, y, linearCoeffs);
        double r2 = rPearson * rPearson;

        result.setCoeffsLinear(linearCoeffs);
        result.setMseLinear(mse[0]);
        result.setRPearsonLinear(rPearson);
        result.setR2Linear(r2);

        if (r2 >= 0.95) {
            result.setTextR2("модель хорошо описывает явление");
        } else if (r2 >= 0.75) {
            result.setTextR2("модель в целом адекватно описывает явление");
        } else if (r2 >= 0.5) {
            result.setTextR2("модель слабо описывает явление");
        } else {
            result.setTextR2("точность аппроксимации недостаточна, модель требует изменения");
        }

        // Полиномиальная аппроксимация 2-й степени
        double[] quadCoeffs = ApproximationMethods.fitQuadratic(x, y);
        double[] yQuad = new double[x.length];
        double[] eQuad = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            yQuad[i] = quadCoeffs[0] * x[i] * x[i] + quadCoeffs[1] * x[i] + quadCoeffs[2];
            eQuad[i] = y[i] - yQuad[i];
        }
        mse[1] = calculateMSE(eQuad);
        result.setQuadCoeffs(quadCoeffs);
        result.setMseQuad(mse[1]);

        // Полиномиальная аппроксимация 3-й степени
        double[] cubicCoeffs = ApproximationMethods.fitCubic(x, y);
        double[] yCubic = new double[x.length];
        double[] eCubic = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            yCubic[i] = cubicCoeffs[0] * x[i] * x[i] * x[i] + cubicCoeffs[1] * x[i] * x[i] + cubicCoeffs[2] * x[i] + cubicCoeffs[3];
            eCubic[i] = y[i] - yCubic[i];
        }
        mse[2] = calculateMSE(eCubic);
        result.setCubicCoeffs(cubicCoeffs);
        result.setMseCubic(mse[2]);

        // Экспоненциальная аппроксимация
        double[] expCoeffs = ApproximationMethods.fitExponential(x, y);
        double[] yExp = new double[x.length];
        double[] eExp = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            yExp[i] = expCoeffs[0] * Math.exp(expCoeffs[1] * x[i]);
            eExp[i] = y[i] - yExp[i];
        }
        mse[3] = calculateMSE(eExp);
        result.setExpCoeffs(expCoeffs);
        result.setMseExp(mse[3]);

        // Логарифмическая аппроксимация
        double[] logCoeffs = ApproximationMethods.fitLogarithmic(x, y);
        double[] yLog = new double[x.length];
        double[] eLog = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            yLog[i] = logCoeffs[0] * Math.log(x[i]) + logCoeffs[1];
            eLog[i] = y[i] - yLog[i];
        }
        mse[4] = calculateMSE(eLog);
        result.setLogCoeffs(logCoeffs);
        result.setMseLog(mse[4]);

        // Степенная аппроксимация
        double[] powerCoeffs = ApproximationMethods.fitPower(x, y);
        double[] yPower = new double[x.length];
        double[] ePower = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            yPower[i] = powerCoeffs[0] * Math.pow(x[i], powerCoeffs[1]);
            ePower[i] = y[i] - yPower[i];
        }
        mse[5] = calculateMSE(ePower);
        result.setPowerCoeffs(powerCoeffs);
        result.setMsePower(mse[5]);

        // Определение лучшей функции по MSE
        int bestIndex = 0;
        for (int i = 1; i < mse.length; i++) {
            if (mse[i] < mse[bestIndex]) {
                bestIndex = i;
            }
        }
        String bestFunction = functionNames[bestIndex];
        result.setFunctionBest(bestFunction);

        double[] yBest = new double[x.length];
        double[] eBest = new double[x.length];
        switch (bestIndex) {
            case 0:
                yBest = yLinear;
                eBest = eLinear;
                break;
            case 1:
                yBest = yQuad;
                eBest = eQuad;
                break;
            case 2:
                yBest = yCubic;
                eBest = eCubic;
                break;
            case 3:
                yBest = yExp;
                eBest = eExp;
                break;
            case 4:
                yBest = yLog;
                eBest = eLog;
                break;
            case 5:
                yBest = yPower;
                eBest = ePower;
                break;
        }
        result.setYBest(yBest);
        result.setEBest(eBest);

        return result;
    }
}
