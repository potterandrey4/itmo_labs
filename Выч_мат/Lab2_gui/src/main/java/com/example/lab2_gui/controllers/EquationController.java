package com.example.lab2_gui.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.example.lab2_gui.math.FunctionsNE;
import com.example.lab2_gui.math.MethodsForNE;
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
				showErrorAlert("Значение eps должно быть больше 0 и меньше 1.");
				return;
			}

			if (!isValidRange(aValue, bValue)) {
				showErrorAlert("Значение a должно быть строго больше b");
				return;
			}

			String selectedId = getSelectedEquationId();
			Function<Double, Double> function;
			Function<Double, Double> derivativeFunction;
			Function<Double, Double> derivativeDerivativeFunction;

			DecimalFormat df = new DecimalFormat("##.#######");

			double[] bisectionMethodRoot;
			double[] newtonMethonRoot;
			double initialApproximation;
			double[] simpleIterRoot;


			switch (selectedId) {
				case "choiceEquation1":

					function = FunctionsNE::function1;
					derivativeFunction = FunctionsNE::derivativeFunction1;
					derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction1;
					break;

				case "choiceEquation2":

					function = FunctionsNE::function2;
					derivativeFunction = FunctionsNE::derivativeFunction2;
					derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction2;
					break;

				case "choiceEquation3":
					function = FunctionsNE::function3;
					derivativeFunction = FunctionsNE::derivativeFunction3;
					derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction4;
					break;

				case "choiceEquation4":
					function = FunctionsNE::function4;
					derivativeFunction = FunctionsNE::derivativeFunction4;
					derivativeDerivativeFunction = FunctionsNE::derivativeDerivativeFunction4;
					break;

				default:
					throw new IllegalStateException("мяу");
			}

			// вызываем методы
			bisectionMethodRoot = MethodsForNE.bisectionMethod(function, aValue, bValue, epsValue);
			initialApproximation = MethodsForNE.findInitialApproximation(function, derivativeDerivativeFunction, aValue, bValue);
			newtonMethonRoot = MethodsForNE.newtonMethod(function, derivativeFunction, initialApproximation, epsValue);
			simpleIterRoot = MethodsForNE.simpleIterationsMethod(function, derivativeFunction, aValue, bValue, epsValue);

			// отправляем данные на новую страницу
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab2_gui/views/result-equation-view.fxml"));
			Parent root = loader.load();

			ResultEquationController resultController = loader.getController();
			resultController.setResultData(bisectionMethodRoot, newtonMethonRoot, simpleIterRoot);

			Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();

		} catch (NumberFormatException e) {
			showErrorAlert("Пожалуйста, введите корректные значения для a, b и eps.");
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
