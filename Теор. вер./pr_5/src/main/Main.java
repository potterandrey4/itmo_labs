package main;

import java.util.ArrayList;

import static main.Tasks.*;

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

		System.out.println("\nВариационный ряд: " + task1(values));

		ArrayList<String> resTask2 = task2(values);
		System.out.println("Экстремальное значение (максимум): " + resTask2.get(0));
		System.out.println("Экстремальное значение (минимум): " + resTask2.get(1));
		System.out.println("Размах: " + resTask2.get(2));


		String resTask3_1 = calculationExpectedValue(values);
		System.out.println("Оценка математического ожидания: " + resTask3_1);

		String res3_2 = calculationStandardDeviation(values);
		System.out.println("Оценка среднеквадратического отклонения: " + res3_2);

		String res4 = task4(values, 1);
		System.out.println("Численные значения ЭФР: " + res4);

		task5_1(values);
		task5_2(values);
	}
}