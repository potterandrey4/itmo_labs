package computation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ы {

    // Метод для проверки диагонального преобладания матрицы
    public static boolean checkDiagonal(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    sum += Math.abs(matrix[i][j]);
                }
            }
            if (Math.abs(matrix[i][i]) < sum) {
                return false;
            }
        }
        return true;
    }

    // Метод для проверки всех перестановок строк матрицы
    public static double[][] doPermuts(double[][] matrix) {
        int n = matrix.length;
        List<int[]> rowPermutations = permutations(n);
        for (int[] rowPerm : rowPermutations) {
            double[][] permMatrix = new double[n][n + 1];
            for (int i = 0; i < n; i++) {
                permMatrix[i] = matrix[rowPerm[i]].clone();
            }
            if (checkDiagonal(permMatrix)) {
                return permMatrix;
            }
        }
        System.out.println("Невозможно достигнуть диагонального преобладания.");
        return null;
    }

    // Метод для создания всех перестановок индексов строк
    public static List<int[]> permutations(int n) {
        List<int[]> permutations = new ArrayList<>();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i;
        }
        generatePermutations(nums, n, permutations);
        return permutations;
    }

    // Рекурсивный метод для генерации всех перестановок
    private static void generatePermutations(int[] nums, int n, List<int[]> permutations) {
        if (n == 1) {
            permutations.add(nums.clone());
        } else {
            for (int i = 0; i < n; i++) {
                swap(nums, i, n - 1);
                generatePermutations(nums, n - 1, permutations);
                swap(nums, i, n - 1);
            }
        }
    }

    // Метод для обмена элементов в массиве
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    public static double[][] generateMatrix(int n) {
        Random rand = new Random();
        double[][] matrix = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                matrix[i][j] = rand.nextDouble() * 10; // Заполняем случайными числами от 0 до 10
            }
        }

        return matrix;
    }




    public static double calcDet(double[][] matrix) {
        int n = matrix.length;

        // Базовый случай для матрицы 1x1
        if (n == 1) {
            return matrix[0][0];
        }

        // Базовый случай для матрицы 2x2
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinant = 0;

        for (int i = 0; i < n; i++) {
            double[][] subMatrix = getSubMatrix(matrix, 0, i);
            determinant += Math.pow(-1, i) * matrix[0][i] * calcDet(subMatrix);
        }

        return determinant;
    }


    private static double[][] getSubMatrix(double[][] matrix, int excludingRow, int excludingCol) {
        int n = matrix.length;
        double[][] subMatrix = new double[n - 1][n - 1];
        int r = -1;

        for (int i = 0; i < n; i++) {
            if (i == excludingRow) {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < n; j++) {
                if (j == excludingCol) {
                    continue;
                }
                c++;
                subMatrix[r][c] = matrix[i][j];
            }
        }

        return subMatrix;
    }


}
