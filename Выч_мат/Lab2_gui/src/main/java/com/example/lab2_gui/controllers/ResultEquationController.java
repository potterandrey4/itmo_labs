package com.example.lab2_gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.Function;

public class ResultEquationController {
	@FXML
	private Text dih_x;
	@FXML
	private Text dih_f;
	@FXML
	private Text dih_iter;
	@FXML
	private Text newt_x;
	@FXML
	private Text newt_f;
	@FXML
	private Text newt_iter;
	@FXML
	private Text simple_x;
	@FXML
	private Text simple_f;
	@FXML
	private Text simple_iter;
	@FXML
	private Text ab;
	@FXML
	private Text isRange;
	@FXML
	private LineChart<Number, Number> lineChart;

	DecimalFormat df = new DecimalFormat("###.#########");

	public void setResultData(double[] bisectionMethodRoot, double[] newtonMethodRoot, double[] simpleIterRoot, double a, double b, Function<Double, Double> function) {
		dih_x.setText("x: " + df.format(bisectionMethodRoot[0]));
		dih_f.setText("f(x): " + df.format(bisectionMethodRoot[1]));
		dih_iter.setText("Итерации: " + bisectionMethodRoot[2]);

		newt_x.setText("x: " + df.format(newtonMethodRoot[0]));
		newt_f.setText("f(x): " + df.format(newtonMethodRoot[1]));
		newt_iter.setText("Итерации: " + newtonMethodRoot[2]);

		simple_x.setText("x: " + df.format(simpleIterRoot[0]));
		simple_f.setText("f(x): " + df.format(simpleIterRoot[1]));
		simple_iter.setText("Итерации: " + simpleIterRoot[2]);

		ab.setText("[a; b] = [" + a + "; " + b + "]");

		// Clear previous data
		lineChart.getData().clear();

		// Add function graph
		XYChart.Series<Number, Number> functionSeries = new XYChart.Series<>();
		functionSeries.setName("Function");
		double step = (b - a) / 100;
		for (double x = a; x <= b; x += step) {
			functionSeries.getData().add(new XYChart.Data<>(x, function.apply(x)));
		}
		lineChart.getData().add(functionSeries);

		// Add Bisection Method root
		XYChart.Series<Number, Number> bisectionSeries = new XYChart.Series<>();
		bisectionSeries.setName("Bisection Method");
		bisectionSeries.getData().add(new XYChart.Data<>(bisectionMethodRoot[0], bisectionMethodRoot[1]));
		lineChart.getData().add(bisectionSeries);

		// Add Newton Method root
		XYChart.Series<Number, Number> newtonSeries = new XYChart.Series<>();
		newtonSeries.setName("Newton Method");
		newtonSeries.getData().add(new XYChart.Data<>(newtonMethodRoot[0], newtonMethodRoot[1]));
		lineChart.getData().add(newtonSeries);

		// Add Simple Iteration Method root
		XYChart.Series<Number, Number> simpleIterSeries = new XYChart.Series<>();
		simpleIterSeries.setName("Simple Iteration Method");
		simpleIterSeries.getData().add(new XYChart.Data<>(simpleIterRoot[0], simpleIterRoot[1]));
		lineChart.getData().add(simpleIterSeries);
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
