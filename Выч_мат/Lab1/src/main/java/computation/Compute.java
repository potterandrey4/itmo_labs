package computation;

import java.util.Arrays;

public class Compute {

    public static double[] methodSimpleIterations(double[][] matrix, double accuracy) {
        int dimension = matrix.length;
        double[] solution = new double[dimension];                  // текущее приближение (изначанльно заполнено 0)
        double[] previousSolution = new double[dimension];

        int k = 0;

        while (true) {
            k++;
            // Сохраняем предыдущее приближение
            System.arraycopy(solution, 0, previousSolution, 0, dimension);

            // Вычисление нового приближения
            for (int i = 0; i < dimension; i++) {
                double sum = matrix[i][dimension]; // сначала здесь свободный член
                for (int j = 0; j < dimension; j++) {
                    if (j != i) {
                        sum -= matrix[i][j] * solution[j];      // a_ij * x_j
                    }
                }
                solution[i] = sum / matrix[i][i];               // x_i
            }

            boolean stop = true;
            for (int i = 0; i < dimension; i++) {
                if (Math.abs((solution[i] - previousSolution[i]) / solution[i]) > accuracy) {
                    stop = false;
                    break;
                }
            }

            if (stop) {
                System.out.println("\nОтвет был найден за " + k + " итераций");
                break; // стопаемся, если достигнута нужная точность
            }
        }
        return solution;
    }

}
