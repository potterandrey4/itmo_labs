package com.example.lab4_gui.controllers;

import com.example.lab4_gui.math.RandomFunctionGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
import java.util.Arrays;

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
    private Button noiseButton;

    Alert alert = new Alert(Alert.AlertType.NONE);
    XYChart.Series<Number, Number> graphSeries = new XYChart.Series<>();
    @FXML
    public void initialize() {

        dataTable.getItems().clear();
        lineChart.getData().clear();
        // Инициализация TableView и LineChart
        xColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[0])));
        yColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[1])));

        // Добавление слушателя к кнопке
        addPointButton.setOnAction(event -> addPoint());
        noiseButton.setStyle("-fx-background-color: #ff8080;");
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
        if (!seriesExists) {
            lineChart.getData().add(graphSeries);
        }
    }

    public void handleCalculate(ActionEvent event) {

        if (dataTable.getItems().size() < 8) {
            showMessage(Alert.AlertType.WARNING, "Маловато..", "введите от 8 до 12 значений");
        } else {
            ObservableList<Double[]> dataList = dataTable.getItems();
            int size = dataList.size();
            double[] xArray = new double[size];
            double[] yArray = new double[size];

            for (int i = 0; i < size; i++) {
                Double[] point = dataList.get(i);
                xArray[i] = point[0];
                yArray[i] = point[1];
            }
            System.out.println(Arrays.toString(xArray));
            System.out.println(Arrays.toString(yArray));

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab4_gui/views/result-view.fxml"));
                Parent root = loader.load();

                ResultController resultController = loader.getController();
                resultController.setInputData(xArray, yArray);
                resultController.initializeData();

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    String fxId = "";

    @FXML
    private void setFxId(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        fxId = clickedButton.getId();
        line.setStyle("");
        polinom_2.setStyle("");
        polinom_3.setStyle("");
        exponenta.setStyle("");
        logarifm.setStyle("");
        degree.setStyle("");
        clickedButton.setStyle(
                "-fx-border-width: 2 ; \n" +
                        "-fx-border-color: #5e9bff;" +
                        "-fx-border-radius: 5px"
        );
        fillTable();
    }

    private void fillTable() {
        Double[][] data = RandomFunctionGenerator.generateFunctionDataWithNoise(fxId, 12, isButtonPressed);

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

    public void clearAll() {
        dataTable.getItems().clear();
        graphSeries.getData().clear();
        lineChart.getData().clear();
    }

    private boolean isButtonPressed = false;

    public void setNoise() {
        isButtonPressed = !isButtonPressed;
        if (isButtonPressed) noiseButton.setStyle("-fx-background-color: #80ff80;");
        else noiseButton.setStyle("-fx-background-color: #ff8080;");
        if (fxId.length() > 0) fillTable();
    }
}
