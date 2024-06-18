package com.example.lab2_gui;

import java.util.ArrayList;
import java.util.List;

public class GraphDataEquation {
	public List<Double> xValues;
	public List<Double> yValues;
	public double rootX;
	public double rootY;
	public String name;
	public int iterations;

	public GraphDataEquation(String name) {
		this.name = name;
		this.xValues = new ArrayList<>();
		this.yValues = new ArrayList<>();
	}

	public void addPoint(double x, double y) {
		this.xValues.add(x);
		this.yValues.add(y);
	}

	public void setRoot(double x, double y) {
		this.rootX = x;
		this.rootY = y;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
