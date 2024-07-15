package com.example.lab5_gui.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class FunctionGenerator {

    private static final Map<String, Function<Double, Double>> functions = new HashMap<>();

    static {
        // Определение функций
        functions.put("line", x -> x + Math.sin(x));
        functions.put("polinom_2", x -> Math.pow(x, 3) - x + 5.0);
        functions.put("polinom_3", x -> 3*Math.log(x) + 2);
    }

    public static double getFactX(String fxId, double x) {
        return functions.get(fxId).apply(x);
    }

    public static Double[][] generateFunctionDataWithNoise(String fxId, int count) {
        Function<Double, Double> function = functions.get(fxId);
        if (function == null) {
            throw new IllegalArgumentException("Функция с идентификатором '" + fxId + "' не найдена.");
        }

        Double[][] result = new Double[count][2];
        for (int i = 0; i < count; i++) {
            double x = i + 1; // Предполагаем, что x начинается с 1
            double y = function.apply(x); // Применяем функцию
            result[i][0] = x; // Значение x
            result[i][1] = y; // Значение y
        }
        return result;
    }


    public static ArrayList<double[]> generateFunctionDataWithNoiseForInterpolation(String fxId, int nodes_interpolation) {
        Function<Double, Double> function = functions.get(fxId);

        ArrayList<double[]> result = new ArrayList<>();
        double[] xArr = linspace(1, 10, nodes_interpolation);
        for (int i = 0; i < nodes_interpolation; i++) {
            double x = xArr[i];
            double y = function.apply(x);
            result.add(new double[]{x, y});
        }
        return result;
    }

    private static double[] linspace(double a, double b, int c) {
        double[] result = new double[c];
        double step = (b - a) / (c - 1); // Расчет шага

        for (int i = 0; i < c; i++) {
            result[i] = a + i * step; // Заполнение массива значениями
        }

        return result;
    }

}
