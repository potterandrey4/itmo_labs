package com.example.lab5_gui.math;

import com.example.lab5_gui.beans.DataBean;

import static com.example.lab5_gui.math.InterpolationMethods.*;

public class CalculateData {

    public static DataBean apply(double[] rx, double[] ry, double xx) {
        DataBean result = new DataBean();

        double rootLagrange = methodLagrange(rx, ry, xx);
        double rootNewtonSep = methodNewtonSep(rx, ry, xx);
        double rootNewtonEnd = methodNewtonEnd(rx, ry, xx);

        return result;
    }
}
