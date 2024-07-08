package com.example.lab4_gui.controllers;

import com.example.lab4_gui.math.RandomFunctionGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class InputController {
    @FXML
    public Button line, polinom_2, polinom_3, exponenta, logarifm, degree;
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

    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    public void initialize() {
        graphSeries = new XYChart.Series<>();
        dataTable.getItems().clear();
        graphSeries.getData().clear();
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

        // Добавление точки в таблицу
        Double[] point = {x, y};
        if (dataTable.getItems().size() == 12) {
            showMessage(Alert.AlertType.INFORMATION, "Достаточно!", "вы не можете ввести больше 12 значений");
        } else {
            dataTable.getItems().add(point);
        }
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
        // Если серия данных не существует, добавляем ее, иначе - обновляем существующую
        if (!seriesExists) {
            lineChart.getData().add(graphSeries);
        }
    }

    public void handleCalculate(ActionEvent actionEvent) {

        if (dataTable.getItems().size() < 8) {
            showMessage(Alert.AlertType.WARNING, "Маловато..", "введите от 8 до 12 значений");
        } else {
            switchScene(actionEvent, "com/example/lab4_gui/views/result-view.fxml");
        }

    }

    @FXML
    private void fillTable(ActionEvent event) {

        Button clickedButton = (Button) event.getSource();
        String fxId = clickedButton.getId();
        Double[][] data = RandomFunctionGenerator.generateFunctionDataWithNoise(fxId, 12);

        // Очистка таблицы перед заполнением новыми данными
        dataTable.getItems().clear();
        graphSeries.getData().clear();

        // Заполнение таблицы данными
        for (Double[] value : data) {
            dataTable.getItems().add(value);
            addPointGraph(value[0], value[1]);
        }

    }

    private void showMessage(Alert.AlertType type, String title, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.show();
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

    public void clearAll(ActionEvent actionEvent) {
        dataTable.getItems().clear();
        graphSeries.getData().clear();
        lineChart.getData().clear();
    }
}
