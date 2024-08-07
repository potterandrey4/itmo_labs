package com.example.lab4_gui.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class RandomFunctionGenerator {

    private static final Random random = new Random();

    private static final Map<String, Function<Double, Double>> functions = new HashMap<>();

    static {
        // Определение функций
        functions.put("line", x -> 2.0 * x + 1.0);
        functions.put("polinom_2", x -> Math.pow(x, 2) + 1.0);
        functions.put("polinom_3", x -> Math.pow(x, 3) - 1.0);
        functions.put("exponenta", x -> Math.exp(x));
        functions.put("logarifm", x -> Math.log(x));
        functions.put("degree", x -> Math.pow(x, 2.0));
    }

    /**
     * Генерирует псевдослучайные значения для указанной функции.
     *
     * @param fxId  Идентификатор функции
     * @param count Количество значений для генерации
     * @return Массив псевдослучайных значений
     */
    public static Double[][] generateFunctionDataWithNoise(String fxId, int count, boolean addNoise) {
        Function<Double, Double> function = functions.get(fxId);
        if (function == null) {
            throw new IllegalArgumentException("Функция с идентификатором '" + fxId + "' не найдена.");
        }

        Double[][] result = new Double[count][2];
        for (int i = 0; i < count; i++) {
            double x = i + 1; // Предполагаем, что x начинается с 1
            double y = function.apply(x); // Применяем функцию
            if (addNoise) {
                double noise = random.nextGaussian() * 0.05 * y; // Генерация соразмерного y случайного шума
                y += noise; // Добавляем шум
            }
            result[i][0] = x; // Значение x
            result[i][1] = y; // Значение y
        }
        return result;
    }

}
