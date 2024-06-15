package com.example.lab2_gui.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class SystemEquationsController {

	@FXML
	private ToggleGroup equationGroup;

	@FXML
	private RadioButton choiceSystem1;

	@FXML
	private RadioButton choiceSystem2;

	@FXML
	private TextField x;

	@FXML
	private TextField y;

	@FXML
	private TextField eps;

	@FXML
	void initialize() {
		equationGroup = new ToggleGroup();
		choiceSystem1.setToggleGroup(equationGroup);
		choiceSystem2.setToggleGroup(equationGroup);
		// Установка первого уравнения по умолчанию
		choiceSystem1.setSelected(true);

		addDoubleValidation(x);
		x.setText("1");
		addDoubleValidation(y);
		y.setText("2");
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

	private String getSelectedSystemId() {
		RadioButton selectedRadioButton = (RadioButton) equationGroup.getSelectedToggle();
		if (selectedRadioButton == null) {
			return null;
		}
		return selectedRadioButton.getId();
	}

	@FXML
	private void handleCalculate(ActionEvent event) {
		try {
			double xValue = Double.parseDouble(x.getText());
			double yValue = Double.parseDouble(y.getText());
			double epsValue = Double.parseDouble(eps.getText());

			if (!isValidEps(eps.getText())) {
				showAlert("Ошибка", "Значение eps должно быть больше 0 и меньше 1.");
				return;
			}

			String selectedId = getSelectedSystemId();
			System.out.println(selectedId);

			switch (selectedId) {
				case "choiceEquation1":
					// calculate first system
					break;
				case "choiceEquation2":
					// calculate second system
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
