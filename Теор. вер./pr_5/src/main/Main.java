import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	public static void main(String[] args) {

		ArrayList<Double> values = new ArrayList<>();
		values.add(-0.03);
		values.add(0.73);
		values.add(-0.59);
		values.add(-1.59);
		values.add(0.38);
		values.add(1.49);
		values.add(0.14);
		values.add(-0.62);
		values.add(-1.59);
		values.add(1.45);
		values.add(-0.38);
		values.add(-1.49);
		values.add(-0.15);
		values.add(0.63);
		values.add(0.06);
		values.add(-1.59);
		values.add(0.61);
		values.add(0.62);
		values.add(-0.05);
		values.add(1.56);

		Collections.sort(values);

		System.out.println("Исходные данные: " + values);

		System.out.println("\nВариационный ряд: " + Tasks.task1(values));

		ArrayList<Double> resTask2 = Tasks.task2(values);
		System.out.println("Экстремальное значение (максимум): " + resTask2.get(0));
		System.out.println("Экстремальное значение (минимум): " + resTask2.get(1));
		System.out.println("Размах: " + resTask2.get(2));


		String resTask3_1 = Tasks.calculationExpectedValue(values);
		System.out.println("Оценка математического ожидания: " + resTask3_1);

		String res3_2 = Tasks.calculationStandardDeviation(values);
		System.out.println("Оценка среднеквадратического отклонения: " + res3_2);

		List<Object[]> result = Tasks.task4(values);
		Tools.printFunctionValues(result);

		Tasks.task5_1(values);
		Tasks.task5_2(values);

		System.out.println(Tools.getWidthsStolbicks(values));
	}
}