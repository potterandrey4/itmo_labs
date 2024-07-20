package com.example.lab6_gui.math;

import com.example.lab6_gui.beans.DataBean;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.example.lab6_gui.math.DiffMethods.*;

public class CalculateData {

    static BiFunction<Double, Double, Double> f1 = (x, y) -> x + y;
    static BiFunction<Double, Double, Double> f2 = (x, y) -> Math.sin(x) - y;
    static BiFunction<Double, Double, Double> f3 = (x, y) -> Math.exp(-x) - y * y;


    public static DataBean apply(String funcInput, double x0, double y0, double xn, double e, double h) {
        DataBean result = new DataBean();

        BiFunction<Double, Double, Double> func;
        Function<Double, Double> exactSolution = null;

        switch (funcInput) {
            case "equation1" -> {
                func = f1;
                exactSolution = DiffMethods::exactSolutionF1;
            }
            case "equation2" -> {
                func = f2;
                exactSolution = DiffMethods::exactSolutionF2;
            }
            case "equation3" -> func = f3;
            default -> {
                return null;
            }
        }


        double[] xExact = linespace(x0, xn, (int) ((xn - x0) / h) + 1);
        result.setXExact(xExact);

        if (exactSolution != null) {
            double[] yExact = Arrays.stream(xExact).map(exactSolution::apply).toArray();
            result.setYExact(yExact);
        }

        double[] yEuler = euler(func, y0, x0, xn, h);
        result.setYEuler(yEuler);

        double[] yImprovedEuler = improvedEuler(func, 1, x0, xn, h);
        result.setYImprovedEuler(yImprovedEuler);

        double[] yAdams = adams(func, y0, x0, xn, h, 4);
        result.setYAdams(yAdams);

        double epsilon = rungeRule(func, y0, x0, xn, 0.1, 0.05, 1);
        result.setEpsilon(epsilon);

        return result;
    }

}

