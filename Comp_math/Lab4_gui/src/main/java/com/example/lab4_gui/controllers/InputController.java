package com.example.lab4_gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class InputController {

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
    private XYChart.Series<Number, Number> graphSeries;

    @FXML
    public void initialize() {
        if (graphSeries == null) {
            graphSeries = new XYChart.Series<>();
        }
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

        // Добавление точки в таблицу
        Double[] point = {x, y};
        dataTable.getItems().add(point);

        // Добавление точки на график
        graphSeries.getData().add(new XYChart.Data<>(x, y));
    }

}
