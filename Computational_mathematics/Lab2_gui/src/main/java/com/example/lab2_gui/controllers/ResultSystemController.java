package com.example.lab2_gui.controllers;

import com.example.lab2_gui.MethodDataSystem;
import com.example.lab2_gui.math.FunctionsSystemsNE;
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

public class ResultSystemController {
	@FXML
	private Text x1_val;
	@FXML
	private Text x2_val;
	@FXML
	private Text x1_eps;
	@FXML
	private Text x2_eps;
	@FXML
	private Text iter;
	@FXML
	private LineChart<Number, Number> lineChart;

	DecimalFormat df = new DecimalFormat("###.##########");

	public void setResultData(MethodDataSystem methodData, int choice) {
		x1_val.setText(df.format(methodData.rootX));
		x2_val.setText(df.format(methodData.rootY));
		x1_eps.setText(df.format(methodData.errorX));
		x2_eps.setText(df.format(methodData.errorY));
		iter.setText(String.valueOf(methodData.iterations));

		lineChart.getData().clear();

		double step = 0.25;
		// График функции 1
		XYChart.Series<Number, Number> function1Series = new XYChart.Series<>();
		function1Series.setName("Функция 1");

		for (double y = -8; y <= 8; y += step) {
			if (choice == 1) {
				function1Series.getData().add(new XYChart.Data<>(FunctionsSystemsNE.system1EquationX(y), y));
			} else {
				function1Series.getData().add(new XYChart.Data<>(FunctionsSystemsNE.system2EquationX(y), y));
			}
		}
		lineChart.getData().add(function1Series);
		for (XYChart.Data<Number, Number> data : function1Series.getData()) {
			data.getNode().setVisible(false);

		}

		// График функции 2
		XYChart.Series<Number, Number> function2Series = new XYChart.Series<>();
		function2Series.setName("Функция 2");
		for (double x = -8; x <= 8; x += step) {
			if (choice == 1) {
				function2Series.getData().add(new XYChart.Data<>(x, FunctionsSystemsNE.system1EquationY(x)));
			} else {
				function2Series.getData().add(new XYChart.Data<>(x, FunctionsSystemsNE.system2EquationY(x)));
			}
		}

		lineChart.getData().add(function2Series);
		for (XYChart.Data<Number, Number> data : function2Series.getData()) {
			data.getNode().setVisible(false);
		}

		XYChart.Series<Number, Number> root = new XYChart.Series<>();
		root.setName("Корень");
		XYChart.Data<Number, Number> rootPoint2 = new XYChart.Data<>(methodData.rootX, methodData.rootY);
		root.getData().add(rootPoint2);
		lineChart.getData().add(root);
	}


//	private void setDataLineChart(double xRoot, double yRoot, XYChart.Series<Number, Number> bisectionSeries) {
//		XYChart.Data<Number, Number> bisectionRootPoint = new XYChart.Data<>(xRoot, yRoot);
//		bisectionSeries.getData().add(bisectionRootPoint);
//		lineChart.getData().add(bisectionSeries);
//		for (XYChart.Data<Number, Number> data : bisectionSeries.getData()) {
//			if (data != bisectionRootPoint) {
//				data.getNode().setVisible(false);
//			}
//		}
//	}
//
//	public static XYChart.Series<Number, Number> convertToSeries(List<Double[]> dotsValues, String name) {
//		XYChart.Series<Number, Number> series = new XYChart.Series<>();
//		series.setName(name);
//
//		for (Double[] dotsValue : dotsValues) {
//			series.getData().add(new XYChart.Data<>(dotsValue[0], dotsValue[1]));
//		}
//		return series;
//	}

	@FXML
	private void handleReturn(ActionEvent actionEvent) {
		switchScene(actionEvent, "/com/example/lab2_gui/views/systemEquations-view.fxml");
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
