package com.example.lab2_gui;

import java.util.ArrayList;
import java.util.List;

public class MethodDataSystem {
	public List<Double[]> dotsValuesSystem1;
	public List<Double[]> dotsValuesSystem2;

	public List<Double[]> dotsSimpleIterations;
	public double rootX;
	public double rootY;
	public String name;
	public int iterations;
	public double errorX;
	public double errorY;

	public MethodDataSystem(String name) {
		this.name = name;
		this.dotsValuesSystem1 = new ArrayList<>();
		this.dotsValuesSystem2 = new ArrayList<>();
		this.dotsSimpleIterations = new ArrayList<>();
	}

	public void addPointSystem1(double x, double y) {
		this.dotsValuesSystem1.add(new Double[]{x, y});
	}

	public void addPointSystem2(double x, double y) {
		this.dotsValuesSystem2.add(new Double[]{x, y});
	}

	public void addPoint(double x, double y) {
		this.dotsSimpleIterations.add(new Double[]{x, y});
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
