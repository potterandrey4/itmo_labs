import computation.ы;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static computation.Compute.methodSimpleIterations;


public class Main {

    public static void main(String[] args) {

        double[][] matrix = new double[0][];
        int dimension = 0;
        double accuracy = 0;
        double[][] permutetedMatrix = new double[0][];
        double[] solution;


        Scanner console_sc = new Scanner(System.in);

        // чтение матрицы
        System.out.println("Выберите источник матрицы: \n\tконсоль - 1\n\tфайл - 2\n\tсгенерировать - 3");
        int mode = Integer.parseInt(console_sc.nextLine());
        if (mode == 1) {

            System.out.println("Введите размерность матрицы (целое положительное число)");
            while (dimension <= 0) {
                if (console_sc.hasNextInt()) {
                    dimension = Integer.parseInt(console_sc.nextLine());
                } else {
                    System.out.println("Введите целое положительное число");
                    console_sc.nextLine();
                }
            }

            System.out.println("Введите матрицу. Столбцы разделяйте пробелами, свободные коэфы отделите вертикальной линией");
            matrix = new double[dimension][dimension + 1];

            try {
                for (int i = 0; i < dimension; i++) {
                    String[] rowElements = console_sc.nextLine().replace("| ", "").split(" ");
                    for (int j = 0; j < dimension + 1; j++) {
                        matrix[i][j] = Double.parseDouble(rowElements[j].replace(",", "."));
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Вы ошиблись в введении матрицы. Попробуйте ещё раз");
                System.exit(52);
            }


            System.out.println("Введите точность");
            accuracy = Double.parseDouble(console_sc.nextLine().replace(",", "."));

        }

        else if (mode == 2) {

            System.out.println("Формат файла: 1я строка содержит в себе N размерность матрицы, последующие N - саму матрицу, далее строка содержащая точнось");
            System.out.println("Введите в консоль абсолютный путь до файла");

            String filePath = console_sc.nextLine();
            File file = new File(filePath);
            Scanner f_sc;
            try {
                f_sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            dimension = Integer.parseInt(f_sc.nextLine());

            matrix = new double[dimension][dimension + 1];

            for (int i = 0; i < dimension; i++) {
                String[] rowElements = f_sc.nextLine().replace("| ", "").split(" ");
                for (int j = 0; j < dimension + 1; j++) {
                    matrix[i][j] = Double.parseDouble(rowElements[j].replace(",", "."));
                }
            }
            accuracy = Double.parseDouble(f_sc.nextLine().replace(",", "."));

            f_sc.close();

            // вывод матрицы
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension+1; j++) {
                    if (j == dimension) System.out.print("| ");
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        }

        else if (mode == 3) {
            System.out.println("Введите размерность матрицы");
            dimension = Integer.parseInt(console_sc.nextLine());
            System.out.println("Введите точность");
            accuracy = Double.parseDouble(console_sc.nextLine().replace(",", "."));

            permutetedMatrix = ы.generateMatrix(dimension);

            System.out.println("Сгененированная матрица");
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension+1; j++) {
                    if (j == dimension) System.out.print("| ");
                    System.out.print(permutetedMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }

        else {
            System.out.println("Введите одно из чисел: 1, 2, 3");
        }


        double determinant = ы.calcDet(matrix);
        if (determinant == 0) {
            System.out.println("Решение невозможно, завершаюсь");
            System.exit(0);
        }


        // сделаем проверку диагонального преобладания
        if (ы.checkDiagonal(matrix)) {
            System.out.println("Матрица диагонально преобладающая");
        }
        else {

            System.out.println("Матрица не диагонально преобладающая, выполняем перестановки");


            permutetedMatrix = ы.doPermuts(matrix);

            if (permutetedMatrix == null) {
                System.exit(-1);
            }

            // вывод новой "переставленной" матрицы
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension+1; j++) {
                    if (j == dimension) System.out.print("| ");
                    System.out.print(permutetedMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }

        solution = methodSimpleIterations(permutetedMatrix, accuracy);
        System.out.println("Решение: ");
        for (int i = 0; i < solution.length; i++) {
            System.out.println("x" + (i+1) + " = " + solution[i]);
        }

    }

}