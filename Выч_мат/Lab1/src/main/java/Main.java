import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	// Метод для решения СЛАУ методом итераций
	static double[] iter(double[][] a, double[] y, int n, double eps) {
		double[] res = new double[n];
		int i, j;

		// Инициализация начальных приближений
		for (i = 0; i < n; i++) {
			res[i] = y[i] / a[i][i];
		}

		double[] Xn = new double[n];

		do {
			// Рассчет нового приближения
			for (i = 0; i < n; i++) {
				Xn[i] = y[i] / a[i][i];
				for (j = 0; j < n; j++) {
					if (i == j)
						continue;
					else {
						Xn[i] -= a[i][j] / a[i][i] * res[j];
					}
				}
			}

			// Проверка на достижение необходимой точности
			boolean flag = true;
			for (i = 0; i < n - 1; i++) {
				if (Math.abs(Xn[i] - res[i]) > eps) {
					flag = false;
					break;
				}
			}

			// Обновление приближений
			for (i = 0; i < n; i++) {
				res[i] = Xn[i];
			}

			// Если достигнута необходимая точность, выход из цикла
			if (flag)
				break;
		} while (true);

		return res;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Введите количество уравнений: ");
		int n = scanner.nextInt();

		double[][] a;
		double[] y;
		double[] x;

		try {
			Scanner fileScanner;
			if (n == 5) {
				fileScanner = new Scanner(new File("/home/andrey/Документы/itmo_labs/Выч_мат/Lab1/src/main/resources/inArray5.txt"));
			} else {
				fileScanner = new Scanner(new File("/home/andrey/Документы/itmo_labs/Выч_мат/Lab1/src/main/resources/inArray3.txt"));
			}

			y = new double[n];
			a = new double[n][n];

			// Заполнение матрицы коэффициентов и вектора свободных членов из файла
			for (int i = 0; i < n; i++) {
				for (int j = 0; j <= n; j++) {
					if (j != n) {
						a[i][j] = fileScanner.nextDouble();
					} else {
						y[i] = fileScanner.nextDouble();
					}
				}
			}

			fileScanner.close();

			// Вывод матрицы и вектора на экран
			System.out.println("Исходная система уравнений:");
			for (int i = 0; i < n; i++) {
				for (int j = 0; j <= n; j++) {
					if (j != n) {
						System.out.print(a[i][j] + "\t");
					} else {
						System.out.print(" | " + y[i] + "\t");
					}
				}
				System.out.println();
			}

			// Ввод точности с клавиатуры
			System.out.print("Введите точность вычислений (eps): ");
			double eps = scanner.nextDouble();

			// Решение СЛАУ методом итераций с заданной точностью
			x = iter(a, y, n, eps);

			// Вывод результата на экран
			System.out.println("\nРешение СЛАУ:");
			for (int i = 0; i < n; i++) {
				System.out.println("x[" + i + "] = " + x[i]);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Файл не найден.");
		} finally {
			scanner.close();
		}
	}
}
