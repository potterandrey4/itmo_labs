package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static main.GraphForTask5_2.drawGraph5_2;

public class Tasks {
	static DecimalFormat df = new DecimalFormat("#.##");

	// вариационный ряд
	public static ArrayList<Double> task1(ArrayList<Double> values) {
		HashSet<Double> uniqueSet = new HashSet<>(values);
		ArrayList<Double> sortedValues = new ArrayList<>(uniqueSet);
		Collections.sort(sortedValues);
		return sortedValues;
	}


	// экстремальные значение и размах
	public static ArrayList<String> task2(ArrayList<Double> values) {
		if (values.isEmpty()) {
			return null;
		}

		ArrayList<String> result = new ArrayList<>();
		double max = values.get(0);
		double min = values.get(0);
		for (int i = 1; i < values.size(); i++) {
			if (values.get(i) > max) {
				max = values.get(i);
			} else if (values.get(i) < min) {
				min = values.get(i);
			}
		}
		result.add(String.valueOf(max));
		result.add(String.valueOf(min));


		result.add(String.valueOf(df.format(Math.abs(max - min))));

		return result;
	}


	// оценка математического ожидания
	// task3_1
	public static String calculationExpectedValue(ArrayList<Double> values) {
		HashMap<Double, Double> probabilities = Tools.getProbabilities(values);        // кол-ва каждого числа
		double expectation = 0;

		for (Double value : values) {
			expectation += value * probabilities.get(value);
		}
		return df.format(expectation);
	}


	// оценка среднеквадратического отклонения (стандартное отклонение)
	// иначе говоря -- корень дисперсии
	// task3_2
	public static String calculationStandardDeviation(ArrayList<Double> values) {
		return String.valueOf(Math.sqrt(Tools.getDispersion(values)));
	}

	// эмпирическая функция распределения
	// task4
	public static String task4(ArrayList<Double> values, double x) {
		Collections.sort(values);

		// Создание массива для хранения частот
		HashMap<Double, Double> frequencies = new HashMap<>();
		// Подсчет частот для каждого X < x
		int count = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) < x) {
				count++;
			}
			frequencies.put( values.get(i), ((double) count / values.size()) );
		}

		return String.valueOf(frequencies);
	}


	// график и полигон приведенных частот группированной выборки
	// гистограмма приведённых частот группированной выборки
	public static void task5_1(ArrayList<Double> values) {
		GraphForTask5_1.GrGis gr = new GraphForTask5_1.GrGis(values);
		gr.setVisible(true);
	}

	public static void task5_2(ArrayList<Double> values) {
		HashMap<Double, Double> probabilities = Tools.getProbabilities(values);
		drawGraph5_2(probabilities);
	}


}
