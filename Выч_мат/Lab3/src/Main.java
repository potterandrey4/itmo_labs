import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int s = 0;
		double a = 0, b = 0, e = -1;
		int n = 4;        // Начальное значение числа разбиения интервала интегрирования

		Pattern patternPlus = Pattern.compile("\\d+.\\d*|\\d+,\\d*|\\d+");
		Pattern patternMinus = Pattern.compile("\\-\\d+.\\d*|\\-\\d+,\\d*|\\-\\d+");
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

		Matcher matcherPlus;
		Matcher matcherMinus;
		while (b <= a) {
			System.out.println("[a, b]; b<=a");
			System.out.print("Введите значение a: ");
			String inputA = scanner.nextLine();
			matcherPlus = patternPlus.matcher(inputA);
			matcherMinus = patternMinus.matcher(inputA);


			if (matcherPlus.matches() || matcherMinus.matches()) {
				try {
					a = Double.parseDouble(inputA.replace(',', '.'));
				} catch (NumberFormatException e1) {
					System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
					continue;
				}
			} else {
				System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой.");
				continue;
			}

			System.out.print("Введите значение b: ");
			String inputB = scanner.nextLine();
			matcherPlus = patternPlus.matcher(inputB);
			matcherMinus = patternMinus.matcher(inputB);

			if (matcherPlus.matches() || matcherMinus.matches()) {
				try {
					b = Double.parseDouble(inputB.replace(',', '.'));
				} catch (NumberFormatException e2) {
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

		// Чтение точности e
		while (e <= 0 || e >= 1) {
			System.out.print("Введите точность (0 < e < 1): ");
			String inputE = scanner.nextLine();
			Matcher matcher = patternPlus.matcher(inputE);

			if (matcher.matches()) {
				try {
					e = Double.parseDouble(inputE.replace(',', '.'));
					if (e <= 0 || e >= 1) {
						System.out.println("Точность должна быть в диапазоне от 0 до 1. Попробуйте еще раз.");
					}
				} catch (NumberFormatException e3) {
					System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой в интервале (0, 1).");
				}
			} else {
				System.out.println("Неверный формат ввода. Пожалуйста, введите число с плавающей точкой в интервале (0, 1).");
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