package computation;

import static java.lang.Math.abs;

public class DiagonalTransformation {
    public static boolean checkDiagonal(double[][] matrix, int size) {
        int i, j, k = 1;
        double sum = 0;
        for (i = 0; i < size; i++) {
            sum = 0;
            for (j = 0; j < size; j++) {
                sum += abs(matrix[i][j]);
            }
            sum -= abs(matrix[i][i]);
            if (sum >= abs(matrix[i][i])) {
                k = 0;
            }
        }
        return (k == 1);
    }
}
