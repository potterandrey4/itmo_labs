package computation;

import java.util.ArrayList;
import java.util.Arrays;
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
                matrix[i][j] = rand.nextDouble() * 10;
            }
        }

        for (int i = 0; i <n; i++) {
            matrix[i][i] = Arrays.stream(matrix[i]).sum();
        }

        return matrix;
    }


    public static double calcDet(double[][] matrix) {
        int n = matrix.length;

        double determinant = 1;

        for (int i = 0; i < n; i++) {
            // зануляем элементы под главной диагональю
            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k < n; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            determinant *= matrix[i][i];
        }

        return determinant;
    }

}
