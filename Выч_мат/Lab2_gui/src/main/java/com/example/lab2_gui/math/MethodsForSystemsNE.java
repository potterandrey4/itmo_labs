package com.example.lab2_gui.math;

import com.example.lab2_gui.MethodDataEquation;
import com.example.lab2_gui.MethodDataSystem;

public class MethodsForSystemsNE {

    public static MethodDataSystem methodOfSimpleIterations(int choice, double x, double y, double eps) {
		MethodDataSystem data = new MethodDataSystem("простых итераций");
        int iterations = 0;
		int maxIterations = 100;
        double xNew, yNew;  // следующее приближение

        if (choice == 1) {
            while (true) {
                xNew = FunctionsSystemsNE.system1EquationX(y);
                yNew = FunctionsSystemsNE.system1EquationY(x);
                iterations++;

                if (Math.abs(xNew - x) <= eps && Math.abs(yNew - y) <= eps) {
                    break;
                }
                x = xNew;
                y = yNew;
				data.addPoint(x, y);

				if (iterations >= maxIterations) {
					break;
				}
            }
        } else {
			while (true) {
				xNew = FunctionsSystemsNE.system2EquationX(y);
				yNew = FunctionsSystemsNE.system2EquationY(x);
				iterations++;

				if (Math.abs(xNew - x) <= eps && Math.abs(yNew - y) <= eps) {
					break;
				}
				x = xNew;
				y = yNew;
				data.addPoint(x, y);

				if (iterations >= maxIterations) {
					break;
				}
			}
        }

		data.setRoot(xNew, yNew);
		data.setIterations(iterations);

		return data;
    }
}
