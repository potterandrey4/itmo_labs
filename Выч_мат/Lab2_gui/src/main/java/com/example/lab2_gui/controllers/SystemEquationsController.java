package com.example.lab2_gui.controllers;

import com.example.lab2_gui.MethodDataEquation;
import com.example.lab2_gui.MethodDataSystem;
import com.example.lab2_gui.math.MethodsForSystemsNE;
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
		x.setText("-5");
		addDoubleValidation(y);
		y.setText("5");
		addDoubleValidation(eps);
		eps.setText("0.001");
	}


	private void addDoubleValidation(TextField textField) {
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("\\d*\\.?\\d*") || newText.matches("-\\d*\\.?\\d*")) {
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
				showErrorAlert("Значение eps должно быть больше 0 и меньше 1.");
				return;
			}

			String selectedId = getSelectedSystemId();
			int choice;
			MethodDataSystem simpleIterationsRoot;

			switch (selectedId) {
				case "choiceSystem1":
					choice = 1;
					simpleIterationsRoot = MethodsForSystemsNE.methodOfSimpleIterations(choice, xValue, yValue, epsValue);
					break;

				case "choiceSystem2":
					choice = 2;
					simpleIterationsRoot = MethodsForSystemsNE.methodOfSimpleIterations(choice, xValue, yValue, epsValue);
					break;
				default:
					throw new IllegalStateException("мяу");
			}

			// Отправка данных на новую страницу
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab2_gui/views/result-systemEquations-view.fxml"));
			Parent root = loader.load();

			ResultSystemController resultController = loader.getController();
			resultController.setResultData(simpleIterationsRoot, choice);

			Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();

		} catch (NumberFormatException e) {
			showErrorAlert("Пожалуйста, введите корректные значения для x, y и eps.");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		alert.setTitle("Ошибка");
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
