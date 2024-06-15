package com.example.lab2_gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EquationController {

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
	private TextField a;

	@FXML
	private TextField b;

	@FXML
	private TextField eps;

	@FXML
	void initialize() {
		// Создание ToggleGroup и добавление RadioButton
		equationGroup = new ToggleGroup();
		choiceEquation1.setToggleGroup(equationGroup);
		choiceEquation2.setToggleGroup(equationGroup);
		choiceEquation3.setToggleGroup(equationGroup);
		choiceEquation4.setToggleGroup(equationGroup);
		// Установка первого уравнения по умолчанию
		choiceEquation1.setSelected(true);

		addDoubleValidation(a);
		a.setText("1");
		addDoubleValidation(b);
		b.setText("2");
		addDoubleValidation(eps);
		eps.setText("0.5");
	}


	private void addDoubleValidation(TextField textField) {
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("\\d*\\.?\\d*")) {
				return change;
			}
			return null;
		};
		textField.setTextFormatter(new TextFormatter<>(filter));
	}


	private boolean isValidEps(String value) {
		try {
			double epsValue = Double.parseDouble(value);
			return epsValue > 0 && epsValue < 1;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isValidRange(double a, double b) {
		return b >= a;
	}

	private String getSelectedEquationId() {
		RadioButton selectedRadioButton = (RadioButton) equationGroup.getSelectedToggle();
		if (selectedRadioButton == null) {
			return null;
		}
		return selectedRadioButton.getId();
	}

	@FXML
	private void handleCalculate(ActionEvent event) {
		try {
			double aValue = Double.parseDouble(a.getText());
			double bValue = Double.parseDouble(b.getText());
			double epsValue = Double.parseDouble(eps.getText());

			if (!isValidEps(eps.getText())) {
				showAlert("Ошибка", "Значение eps должно быть больше 0 и меньше 1.");
				return;
			}

			if (!isValidRange(aValue, bValue)) {
				showAlert("Ошибка", "Значение a должно быть строго больше b");
				return;
			}

			String selectedId = getSelectedEquationId();
			System.out.println(selectedId);

			switch (selectedId) {
				case "choiceEquation1":
					System.out.println("Выбрано уравнение: " + "x^3 - 2.561x^2 - 1.325x + 4.395 = 0");
					break;
				case "choiceEquation2":
					System.out.println("Выбрано уравнение: " + "-1.8x^3 - 2.94x^2 + 10.37x + 5.38 = 0");
					break;
				case "choiceEquation3":
					System.out.println("Выбрано уравнение: " + "-2.4x^3 + 1.27x^2 - 8.63x + 2.31 = 0");
					break;
				case "choiceEquation4":
					System.out.println("Выбрано уравнение: " + "-1.38x^3 - 5.42x^2 + 2.57x + 10.95 = 0");
					break;
			}

		} catch (NumberFormatException e) {
			showAlert("Ошибка", "Пожалуйста, введите корректные значения для a, b и eps.");
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.showAndWait();
	}


	@FXML
	private void handleReturn(ActionEvent actionEvent) {
		switchScene(actionEvent, "/com/example/lab2_gui/views/index-view.fxml");
	}

	private void switchScene(ActionEvent event, String fxmlFilePath) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
			Parent root = loader.load();
			Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
