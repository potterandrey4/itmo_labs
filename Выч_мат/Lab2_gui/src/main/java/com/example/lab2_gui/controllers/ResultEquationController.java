package com.example.lab2_gui.controllers;

import com.example.lab2_gui.GraphDataEquation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	public void setResultData(GraphDataEquation bisectionMethodRoot, GraphDataEquation newtonMethodData, GraphDataEquation simpleIterRoot, double a, double b, Function<Double, Double> function) {
		dih_x.setText("x: " + df.format(bisectionMethodRoot.rootX));
		dih_f.setText("f(x): " + df.format(bisectionMethodRoot.rootY));
		dih_iter.setText("Итерации: " + bisectionMethodRoot.iterations);

		newt_x.setText("x: " + df.format(newtonMethodData.rootX));
		newt_f.setText("f(x): " + df.format(newtonMethodData.rootY));
		newt_iter.setText("Итерации: " + newtonMethodData.iterations);

		simple_x.setText("x: " + df.format(simpleIterRoot.rootX));
		simple_f.setText("f(x): " + df.format(simpleIterRoot.rootY));
		simple_iter.setText("Итерации: " + simpleIterRoot.iterations);

		ab.setText("[a; b] = [" + a + "; " + b + "]");

		lineChart.getData().clear();

		List<GraphDataEquation> graphDataList = new ArrayList<>();

		// График функции
		GraphDataEquation functionData = new GraphDataEquation("Функция");
		XYChart.Series<Number, Number> functionSeries = new XYChart.Series<>();
		functionSeries.setName("Функция");
		double step = (b - a) / 50;
		double coefficient = Math.abs(a + b) / 2 / 20;
		for (double x = a - coefficient; x <= b + coefficient; x += step) {
			functionSeries.getData().add(new XYChart.Data<>(x, function.apply(x)));
			functionData.addPoint(x, function.apply(x));
		}
		lineChart.getData().add(functionSeries);
		for (XYChart.Data<Number, Number> data : functionSeries.getData()) {
			data.getNode().setVisible(false);
		}

		// График метода дихотомии
		XYChart.Series<Number, Number> bisectionSeries = convertToSeries(bisectionMethodRoot.dotsValues, "График дихотомии");
		setDataLineChart(bisectionMethodRoot.rootX, bisectionMethodRoot.rootY, bisectionSeries);

		// Метод Ньютона
		XYChart.Series<Number, Number> newtonSeries = convertToSeries(newtonMethodData.dotsValues, "График Ньютона");
		setDataLineChart(newtonMethodData.rootX, newtonMethodData.rootY, newtonSeries);

		// Метод простых итераций
		XYChart.Series<Number, Number> simpleIterSeries = convertToSeries(simpleIterRoot.dotsValues, "График простых итераций");
		setDataLineChart(simpleIterRoot.rootX, simpleIterRoot.rootY, simpleIterSeries);

		graphDataList.add(functionData);
		graphDataList.add(bisectionMethodRoot);
		graphDataList.add(newtonMethodData);
		graphDataList.add(simpleIterRoot);

		runPlotPy(graphDataList);
	}

	private void setDataLineChart(double xRoot, double yRoot, XYChart.Series<Number, Number> bisectionSeries) {
		XYChart.Data<Number, Number> bisectionRootPoint = new XYChart.Data<>(xRoot, yRoot);
		bisectionSeries.getData().add(bisectionRootPoint);
		lineChart.getData().add(bisectionSeries);
		for (XYChart.Data<Number, Number> data : bisectionSeries.getData()) {
			if (data != bisectionRootPoint) {
				data.getNode().setVisible(false);
			}
		}
	}

	public static XYChart.Series<Number, Number> convertToSeries(List<Double[]> dotsValues, String name) {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(name);

		for (Double[] dotsValue : dotsValues) {
			series.getData().add(new XYChart.Data<>(dotsValue[0], dotsValue[1]));
		}
		return series;
	}


	@FXML
	private void handleReturn(ActionEvent actionEvent) {
		switchScene(actionEvent, "/com/example/lab2_gui/views/equation-view.fxml");
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

	private static void runPlotPy(List<GraphDataEquation> graphDataList) {
		String pythonInterpreter = "./venv/bin/python";
		String scriptPath = "/home/andrey/Документы/itmo_labs/Выч_мат/Lab2_gui/plot.py";

		List<String> command = new ArrayList<>();
		command.add(pythonInterpreter);
		command.add(scriptPath);

		for (GraphDataEquation data : graphDataList) {
			if (Objects.equals(data.name, "Функция")){
				command.add(data.name);
				command.add("NaN");
				command.add("NaN");
			}
			else {
				command.add(data.name);
				command.add(Double.toString(data.rootX));
				command.add(Double.toString(data.rootY));
			}
			command.add("[" +
					data.dotsValues.stream()
							.map(Arrays::toString)
							.collect(Collectors.joining(", "))
					+ "]"
			);
		}

		ProcessBuilder pb = new ProcessBuilder(command);
		pb.directory(new File("/home/andrey/Документы/itmo_labs/Выч_мат/Lab2_gui"));
		pb.redirectErrorStream(true);

		try {
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			int exitCode = process.waitFor();
			System.out.println("Exited with code: " + exitCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
