package com.example.lab2_gui.math;

public class FunctionsSystemsNE {

	public static String system1ToString() {
		return "\t𝑠𝑖𝑛(𝑦 + 0.5) − 𝑥 = 1" + "\n" +
				"\t𝑦 + 𝑐𝑜𝑠(𝑥 - 2) = 0";
	}

	public static double system1EquationX(double y) {
		return y + 5;
	}

	public static double system1EquationY(double x) {
		return -Math.cos(x - 2);
	}

	public static String system2ToString() {
		return "\t𝑐𝑜𝑠(𝑦) + 𝑥 = 1.5" + "\n" +
				"\t2𝑦 − x^2 = -1";
	}

	public static double system2EquationX(double y) {
		return y/2 - 1;
	}

	public static double system2EquationY(double x) {
		return x/3 + 3;
	}
}
