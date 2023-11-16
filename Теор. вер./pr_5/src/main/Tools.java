package main;

import java.util.*;

import static main.Tasks.task2;

public class Tools {
	// количество каждого элемента в виде map
	private static HashMap<Double, Integer> generateFrequencyMap(ArrayList<Double> values) {
		HashMap<Double, Integer> frequencyMap = new HashMap<>();

		for (Double element : values) {
			if (frequencyMap.containsKey(element)) {
				frequencyMap.put(element, frequencyMap.get(element) + 1);
			} else {
				frequencyMap.put(element, 1);
			}
		}
		return frequencyMap;
	}

	// количество вхождений каждого элемента
	public static ArrayList<Integer> getFrequencies(ArrayList<Double> values) {
		HashMap<Double, Integer> frequencyMap = generateFrequencyMap(values);

		ArrayList<Integer> frequencies = new ArrayList<>();

		for (Double element : values) {
			frequencies.add(frequencyMap.get(element));
		}

		return frequencies;
	}


	// вероятность для каждого элемента
	public static HashMap<Double, Double> getProbabilities(ArrayList<Double> values) {
		HashMap<Double, Integer> frequencyMap = generateFrequencyMap(values);
		;
		int totalElements = values.size();

		// Вычисление вероятностей
		HashMap<Double, Double> probabilities = new HashMap<>();
		for (Double element : values) {
			double probability = (double) frequencyMap.get(element) / totalElements;
			probabilities.put(element, probability);
		}
		return probabilities;
	}

	public static ArrayList<Double> getUniqueValues(ArrayList<Double> rowData) {
		HashSet<Double> uniqueSet = new HashSet<>(rowData);
		ArrayList<Double> sortedValues = new ArrayList<>(uniqueSet);
		Collections.sort(sortedValues);
		return sortedValues;
	}

	public static void printFunctionValues(List<Object[]> functionValues) {
		for (int i = 0; i < functionValues.size(); i++) {
			String[] range = (String[]) functionValues.get(i)[0];
			if (i == functionValues.size() - 1) {
				System.out.println("Range: [" + range[0] + ", +inf], Value: " + 1.00);
			} else {
				double functionValue = (double) functionValues.get(i)[1];
				System.out.println("Range: [" + range[0] + ", " + range[1] + "], Value: " + functionValue);
			}
		}
	}

	// подсчёт промежутка столбца для гистограммы
	public static double getDoubleFor5Task(ArrayList<Double> rowData) {
		ArrayList<Double> maxAndMin = task2(rowData);
		return (maxAndMin.get(0) - maxAndMin.get(1)) / (1 + (Math.log(rowData.size()) / Math.log(2)));
	}

	//Для каждого промежутка рассчитывается количество элементов выборки, попавших в него и на основе отношения этого
	// значения к размеру выборки рассчитывается высота столбика гистограммы для промежутка

	// посчитаем высоту столбцов


}

