package com.example.lab4_gui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Equation {
	private final IntegerProperty choice;
	private final DoubleProperty a;
	private final DoubleProperty b;
	private final DoubleProperty eps;

	public Equation() {
		this(null, null, null, null);
	}

	public Equation(Integer choice, Double a, Double b, Double eps) {
		this.choice = new SimpleIntegerProperty(choice);
		this.a = new SimpleDoubleProperty(a);
		this.b = new SimpleDoubleProperty(b);
		this.eps = new SimpleDoubleProperty(eps);
	}

	public int getChoice() {
		return choice.get();
	}

	public IntegerProperty choiceProperty() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice.set(choice);
	}

	public double getA() {
		return a.get();
	}

	public DoubleProperty aProperty() {
		return a;
	}

	public void setA(double a) {
		this.a.set(a);
	}

	public double getB() {
		return b.get();
	}

	public DoubleProperty bProperty() {
		return b;
	}

	public void setB(double b) {
		this.b.set(b);
	}

	public double getEps() {
		return eps.get();
	}

	public DoubleProperty epsProperty() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps.set(eps);
	}
}
