package com.example.lab2_gui.math;

import java.math.BigDecimal;
import java.math.MathContext;

public class MethodsForSystemsNE {

    public static double[] methodOfSimpleIterations(int choice, double x, double y, double eps) {
        int iteration = 0;
        double xNew, yNew;  // следующее приближение

        if (choice == 1) {
            while (true) {
                xNew = FunctionsSystemsNE.system1EquationX(x, y);
                yNew = FunctionsSystemsNE.system1EquationY(x, y);
                iteration++;
                System.out.printf("Итерация %d: x = %.4f, y = %.4f%n", iteration, xNew, yNew);

                if (Math.abs(xNew - x) <= eps && Math.abs(yNew - y) <= eps) {
                    break;
                }
                x = xNew;
                y = yNew;
            }
        } else {
            while (true) {
                xNew = FunctionsSystemsNE.system2EquationX(x, y);
                yNew = FunctionsSystemsNE.system2EquationY(x, y);
                iteration++;
                System.out.printf("Итерация %d: x = %.4f, y = %.4f%n", iteration, xNew, yNew);

                if (Math.abs(xNew - x) <= eps && Math.abs(yNew - y) <= eps) {
                    break;
                }
                x = xNew;
                y = yNew;
            }
        }
        return new double[]{iteration, x, y};
    }
}
