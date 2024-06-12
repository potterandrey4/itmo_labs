import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int s = 0;
		double a = 0, b = 0, e = -1;
		int n = 100;        // максимальное количество итераций

		Pattern pattern = Pattern.compile("\\d*\\.?\\d+|\\d+,\\d*");
		while (true) {
			System.out.print("Введите номер функции (1-4): ");
			if (scanner.hasNextInt()) {
				s = scanner.nextInt();
				if (s < 1 || s > 4) {
					System.out.println("Номер функции должен быть в диапазоне от 1 до 4. Попробуйте еще раз.");
				} else {
					scanner.nextLine();
					break;
				}
			} else {
				System.out.println("Неверный формат ввода. Пожалуйста, введите целое число.");
				scanner.nextLine();
			}
		}

		while (b <= a) {
			System.out.println("[a, b]; b<=a");
			System.out.print("Введите значение a: ");
			String input = scanner.nextLine();

			Matcher matcher = pattern.matcher(input);

			if (matcher.matches()) {
				try {
					a = Double.parseDouble(input.replace(',', '.'));
				} catch (NumberFormatException exception) {
					System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
					continue;
				}
			} else {
				System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
				continue;
			}

			System.out.print("Введите значение b: ");
			input = scanner.nextLine();

			matcher = pattern.matcher(input);

			if (matcher.matches()) {
				try {
					b = Double.parseDouble(input.replace(',', '.'));
				} catch (NumberFormatException exception) {
					System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
					continue;
				}
			} else {
				System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
				continue;
			}

			if (b <= a) {
				System.out.println("b должно быть больше a. Попробуйте еще раз.");
			}
		}

		while (true) {
			System.out.print("Введите точность (0 < e < 1): ");
			String input = scanner.nextLine();

			Matcher matcher = pattern.matcher(input);

			if (matcher.matches()) {
				try {
					e = Double.parseDouble(input);
					if (e >= 1 || e <= 0) {
						System.out.println("Точность должна быть в диапазоне от 0 до 1. Попробуйте еще раз.");
					} else {
						break;
					}
				} catch (NumberFormatException exception) {
					System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
				}
			} else {
				System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
			}
		}


		FunctionsEnum func = null;
		IntegralsEnum integ = null;

		switch (s) {
			case 1:
				func = FunctionsEnum.FUNCTION_1;
				integ = IntegralsEnum.INTEGRAL_1;
			case 2:
				func = FunctionsEnum.FUNCTION_2;
				integ = IntegralsEnum.INTEGRAL_2;
			case 3:
				func = FunctionsEnum.FUNCTION_3;
				integ = IntegralsEnum.INTEGRAL_3;
			case 4:
				func = FunctionsEnum.FUNCTION_4;
				integ = IntegralsEnum.INTEGRAL_4;
		}


		Methods.checkConvergence(s, a, b);

		int methodNum = 0;
		while (methodNum < 1 || methodNum > 5) {
			System.out.println("Выберите метод интегрирования (введите номер метода):");
			System.out.println("1. Метод средних прямоугольников\n2. Метод правых прямоугольников\n3. Метод левых прямоугольников\n4. Метод трапеций\n5. Метод Симпсона");
			methodNum = scanner.nextInt();
		}

		CustomFunction method;

		switch (methodNum) {
			case 1:
				method = Methods::midpointRule;
				break;
			case 2:
				method = Methods::rightRectangleRule;
				break;
			case 3:
				method = Methods::leftRectangleRule;
				break;
			case 4:
				method = Methods::trapezoidalRule;
				break;
			case 5:
				method = Methods::simpsonRule;
				break;
			default:
				throw new IllegalStateException("Неверный выбор метода");
		}

		System.out.println("Точное значение: " + Methods.exactIntegral(integ, a, b));

		double integralValue = Methods.calculateIntegral(method, func, a, b, e, n);
		System.out.println("Значение интеграла: " + integralValue);
	}

}