package tools;

import java.util.Arrays;
import java.util.List;

public class Validator {
	private final double x, y, r;

	public Validator(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}

	public boolean checkData() {
		if (!checkX()) System.out.println("X validation haven't passed");
		if (!checkY()) System.out.println("Y validation haven't passed");
		if (!checkR()) System.out.println("R validation haven't passed");
		System.out.println(x);
		System.out.println(y);
		System.out.println(r);
		return checkX() && checkY() && checkR();
	}

	private boolean checkX() {
		List<Integer> validValues = Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3, 4);
		return validValues.contains((int) x) && x == (int) x;
	}

	private boolean checkY() {
		return y >= -5 && y <= 5;
	}

	private boolean checkR() {
		List<Integer> validValues = Arrays.asList(1, 2, 3, 4, 5);
		return validValues.contains((int) r) && r == (int) r;
	}
}