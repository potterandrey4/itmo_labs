import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.DoubleStream;

public class Tasks {
	static DecimalFormat df = new DecimalFormat("#.##");

	// вариационный ряд
	public static ArrayList<Double> task1(ArrayList<Double> values) {
		Collections.sort(values);
		return values;
	}


	// экстремальные значение и размах
	public static ArrayList<Double> task2(ArrayList<Double> values) {
		if (values.isEmpty()) {
			return null;
		}

		ArrayList<Double> result = new ArrayList<>();
		double max = values.get(0);
		double min = values.get(0);
		for (int i = 1; i < values.size(); i++) {
			if (values.get(i) > max) {
				max = values.get(i);
			} else if (values.get(i) < min) {
				min = values.get(i);
			}
		}
		result.add(max);
		result.add(min);


		result.add(Double.valueOf(df.format(Math.abs(max - min)).replace(",", ".")));

		return result;
	}


	static double expectationEstimation = 0;

	// оценка математического ожидания
	// task3_1
	public static String calculationExpectedValue(ArrayList<Double> values) {
		double sum = 0;
		Collections.sort(values);
		for (double value : values) {
			sum += value;
		}
		expectationEstimation = sum / values.size();
		return String.valueOf(expectationEstimation);
	}


	// оценка среднеквадратического отклонения (стандартное отклонение)
	// иначе говоря -- корень дисперсии
	// task3_2
	public static String calculationStandardDeviation(ArrayList<Double> values) {
		Collections.sort(values);
		double sumSquaredDifferences = values.stream()
				.flatMapToDouble(it -> DoubleStream.of(Math.pow(it - expectationEstimation, 2)))
				.sum();

		return String.valueOf(Math.sqrt(sumSquaredDifferences / (values.size() - 1)));
	}

	// эмпирическая функция распределения
	// task4
	public static List<Object[]> task4(ArrayList<Double> values) {
		Collections.sort(values);
		ArrayList<Double> uniqueValues = Tools.getUniqueValues(values);
		int size = values.size();
		List<Object[]> frequencies = new ArrayList<>();
		double sum = 0;

		// Обработка первого элемента
		frequencies.add(new Object[]{new String[]{"-inf", String.valueOf(uniqueValues.get(0))}, sum});
		sum += (double) countOccurrences(values, uniqueValues.get(0)) / size;

		// Обработка остальных элементов
		for (int it = 1; it < uniqueValues.size(); ++it) {
			frequencies.add(new Object[]{new String[]{String.valueOf(uniqueValues.get(it - 1)), String.valueOf(uniqueValues.get(it))}, Double.valueOf(df.format(sum).replace(",", "."))});
			sum += (double) countOccurrences(values, uniqueValues.get(it)) / size;
		}

		// Обработка последнего элемента
		frequencies.add(new Object[]{new String[]{String.valueOf(uniqueValues.get(uniqueValues.size() - 1)), "inf"}, 1});

		return frequencies;
	}

	private static long countOccurrences(List<Double> data, double value) {
		return data.stream().filter(v -> v.equals(value)).count();
	}


	// график и полигон приведенных частот группированной выборки
	// гистограмма приведённых частот группированной выборки
	public static void task5_1(ArrayList<Double> values) {
		GraphForTask5_1.GrGis gr = new GraphForTask5_1.GrGis(values);
		gr.setVisible(true);
	}

	public static void task5_2(ArrayList<Double> values) {
		HashMap<Double, Double> probabilities = Tools.getProbabilities(values);
		GraphForTask5_2.drawGraph5_2(probabilities);
	}


}
