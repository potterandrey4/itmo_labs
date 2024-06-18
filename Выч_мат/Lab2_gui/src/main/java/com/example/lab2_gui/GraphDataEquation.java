package com.example.lab2_gui;

import java.util.ArrayList;
import java.util.List;

public class GraphDataEquation {
	public List<Double[]> dotsValues;
	public double rootX;
	public double rootY;
	public String name;
	public int iterations;

	public GraphDataEquation(String name) {
		this.name = name;
		this.dotsValues = new ArrayList<>();
	}

	public void addPoint(double x, double y) {
		this.dotsValues.add(new Double[]{x, y});
	}

	public void setRoot(double x, double y) {
		this.rootX = x;
		this.rootY = y;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
