package com.example.lab4_gui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SystemEquations {

	private final IntegerProperty choice;
	private final DoubleProperty x;
	private final DoubleProperty y;
	private final DoubleProperty eps;

	public SystemEquations() {
		this(null, null, null, null);
	}

	public SystemEquations(Integer choice, Double a, Double b, Double eps) {
		this.choice = new SimpleIntegerProperty(choice);
		this.x = new SimpleDoubleProperty(a);
		this.y = new SimpleDoubleProperty(b);
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

	public double getX() {
		return x.get();
	}

	public DoubleProperty xProperty() {
		return x;
	}

	public void setX(double x) {
		this.x.set(x);
	}

	public double getY() {
		return y.get();
	}

	public DoubleProperty yProperty() {
		return y;
	}

	public void setY(double y) {
		this.y.set(y);
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


