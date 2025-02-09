package com.example.lab5_gui.controllers;

import com.example.lab5_gui.math.FunctionGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class InputController {
    @FXML
    public Button line, polinom_2, polinom_3;
    @FXML
    public TextField function_arg;
    @FXML
    public TextField nodes_interpolation;
    XYChart.Series<Number, Number> graphSeries = new XYChart.Series<>();
    ArrayList<double[]> dataInterpolation = new ArrayList<>();
    String fxId = "";
    @FXML
    private TextField xInput, yInput;
    @FXML
    private Button addPointButton;
    @FXML
    private TableView<Double[]> dataTable;
    @FXML
    private TableColumn<Double[], String> xColumn, yColumn;
    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    public void initialize() {

        dataTable.getItems().clear();
        lineChart.getData().clear();
        // Инициализация TableView и LineChart
        xColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[0])));
        yColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[1])));

        // Добавление слушателя к кнопке
        addPointButton.setOnAction(event -> addPoint());

    }

    @FXML
    private void addPoint() {
        double x = Double.parseDouble(xInput.getText());
        double y = Double.parseDouble(yInput.getText());
        dataInterpolation.add(new double[]{x, y});
        // Добавление точки в таблицу
        Double[] point = {x, y};
        dataTable.getItems().add(point);
        addPointGraph(x, y);
    }

    private void addPointGraph(double x, double y) {
        graphSeries.getData().add(new XYChart.Data<>(x, y));

        // Проверка на существование серии данных в LineChart
        boolean seriesExists = false;
        for (XYChart.Series<Number, Number> existingSeries : lineChart.getData()) {
            if (existingSeries.equals(graphSeries)) {
                seriesExists = true;
                break;
            }
        }
        if (!seriesExists) {
            lineChart.getData().add(graphSeries);
        }
    }

    public void handleCalculate(ActionEvent event) {
        int nodes = Math.min(Integer.parseInt(nodes_interpolation.getText()), dataInterpolation.size());

        double[] rx = new double[nodes];
        double[] ry = new double[nodes];
        for (int i = 0; i < nodes; i++) {
            rx[i] = dataInterpolation.get(i)[0];
            ry[i] = dataInterpolation.get(i)[1];
        }

        ObservableList<Double[]> dataList = dataTable.getItems();
        int size = dataList.size();
        double[] xArray = new double[size];
        double[] yArray = new double[size];

        for (int i = 0; i < size; i++) {
            Double[] point = dataList.get(i);
            xArray[i] = point[0];
            yArray[i] = point[1];
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab5_gui/views/result-view.fxml"));
            Parent root = loader.load();

            ResultController resultController = loader.getController();
            double f_x = FunctionGenerator.getFactX(fxId, Double.parseDouble(function_arg.getText()));
            resultController.setInputData(xArray, yArray, Double.parseDouble(function_arg.getText()), f_x, nodes, rx, ry);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void setFxId(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        fxId = clickedButton.getId();
        line.setStyle("");
        polinom_2.setStyle("");
        polinom_3.setStyle("");
        clickedButton.setStyle(
                "-fx-border-width: 2 ; \n" +
                        "-fx-border-color: #5e9bff;" +
                        "-fx-border-radius: 5px"
        );
        fillTable();
    }

    private void fillTable() {
        dataInterpolation = FunctionGenerator.generateFunctionDataWithNoiseForInterpolation(fxId, Integer.parseInt(nodes_interpolation.getText()));
        Double[][] data = FunctionGenerator.generateFunctionDataWithNoise(fxId, 12);

        // Очистка таблицы перед заполнением новыми данными
        dataTable.getItems().clear();
        graphSeries.getData().clear();

        // Заполнение таблицы данными
        for (Double[] value : data) {
            dataTable.getItems().add(value);
            addPointGraph(value[0], value[1]);
        }

    }

    public void clearAll() {
        dataTable.getItems().clear();
        graphSeries.getData().clear();
        lineChart.getData().clear();
    }
}
