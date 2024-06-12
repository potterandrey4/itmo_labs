package com.example.lab2_gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class InputDataNLEController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField a;

	@FXML
	private TextField b;

	@FXML
	private ToggleGroup equationGroup;

	@FXML
	private RadioButton choiceEquation1;

	@FXML
	private RadioButton choiceEquation2;

	@FXML
	private RadioButton choiceEquation3;

	@FXML
	private RadioButton choiceEquation4;

	@FXML
	private TextField eps;

	@FXML
	void initialize() {
		assert a != null : "fx:id=\"a\" was not injected: check your FXML file 'nle-view.fxml'.";
		assert b != null : "fx:id=\"b\" was not injected: check your FXML file 'nle-view.fxml'.";
		assert choiceEquation1 != null : "fx:id=\"choiceEquation1\" was not injected: check your FXML file 'nle-view.fxml'.";
		assert choiceEquation2 != null : "fx:id=\"choiceEquation2\" was not injected: check your FXML file 'nle-view.fxml'.";
		assert choiceEquation3 != null : "fx:id=\"choiceEquation3\" was not injected: check your FXML file 'nle-view.fxml'.";
		assert choiceEquation4 != null : "fx:id=\"choiceEquation4\" was not injected: check your FXML file 'nle-view.fxml'.";
		assert eps != null : "fx:id=\"eps\" was not injected: check your FXML file 'nle-view.fxml'.";

		// Связываем все RadioButton с одной группой
		choiceEquation1.setToggleGroup(equationGroup);
		choiceEquation2.setToggleGroup(equationGroup);
		choiceEquation3.setToggleGroup(equationGroup);
		choiceEquation4.setToggleGroup(equationGroup);

		// Можно установить один из RadioButton как выбранный по умолчанию
		choiceEquation1.setSelected(true);

	}

}
