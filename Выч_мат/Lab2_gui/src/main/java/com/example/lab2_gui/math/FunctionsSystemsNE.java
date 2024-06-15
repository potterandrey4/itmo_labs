package com.example.lab2_gui.math;

public class FunctionsSystemsNE {
	private double x;
	private double y;

	public FunctionsSystemsNE(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static String system1ToString() {
		return "\t𝑠𝑖𝑛(𝑦 + 0.5) − 𝑥 = 1" + "\n" + "\t𝑦 + 𝑐𝑜𝑠(𝑥 - 2) = 0";
	}

	public static double system1EquationX(double x, double y) {
		return Math.sin(y + 0.5) - 1;
	}

	public static double system1EquationY(double x, double y) {
		return -Math.cos(x - 2);
	}

	public static String system2ToString() {
		return "\t𝑐𝑜𝑠(𝑦) + 𝑥 = 1.5" + "\n" + "\t2𝑦 − 𝑠𝑖𝑛(𝑥 − 0.5) = 1";
	}

	public static double system2EquationX(double x, double y) {
		return 1.5 - Math.cos(y);
	}

	public static double system2EquationY(double x, double y) {
		return (1 - Math.sin(x - 0.5)) / 2;
	}
}
