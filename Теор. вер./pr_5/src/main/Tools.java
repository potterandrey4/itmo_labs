package main;

import java.util.ArrayList;
import java.util.HashMap;

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

	// нахождение дисперсии
	public static double getDispersion(ArrayList<Double> values) {
		HashMap<Double, Double> probabilities = getProbabilities(values);
		ArrayList<Double> setValues = Tasks.task1(values);
		double expectedValue = Double.parseDouble(Tasks.calculationExpectedValue(values).replace(",", "."));

		// M(x^2)
		double expectedValueInQuadrat = 0;
		for (Double element : setValues) {
			expectedValueInQuadrat += (element * element) * probabilities.get(element);
		}

		double dispersion = expectedValueInQuadrat - (expectedValue * expectedValue);
		return dispersion;
	}

}

