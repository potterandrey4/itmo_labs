package com.example.lab2_gui;

import java.util.ArrayList;
import java.util.List;

public class MethodDataSystem {
	public double rootX;
	public double rootY;
	public String name;
	public int iterations;
	public double errorX;
	public double errorY;

	public MethodDataSystem(String name) {
		this.name = name;
	}

	public void setRoot(double x, double y) {
		this.rootX = x;
		this.rootY = y;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public void setErrorX(double errorX) {
		this.errorX = errorX;
	}

	public void setErrorY(double errorY) {
		this.errorY = errorY;
	}
}
