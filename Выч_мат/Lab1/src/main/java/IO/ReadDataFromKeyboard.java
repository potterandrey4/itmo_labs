package IO;

import java.util.ArrayList;
import java.util.Scanner;

public class ReadDataFromKeyboard {

    Integer capacity;

    public Double readPrecision(Scanner in) {
        return in.nextDouble();
    }

    public Integer readCapacity(Scanner in) {
        capacity = in.nextInt();
        while (capacity <= 0 || capacity > 20) {
            System.out.println("Некорректный размер матрицы. Попробуйте ещё раз");
            capacity = in.nextInt();
        }
        return capacity;
    }

    public double[][] readMatrix(Scanner in) {

        double[][] matrix = new double[capacity][capacity];

        System.out.println("Введите элементы матрицы:");
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                matrix[i][j] = in.nextDouble();
            }
        }

        return matrix;

    }
}
