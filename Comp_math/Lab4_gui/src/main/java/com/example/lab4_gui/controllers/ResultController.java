package com.example.lab4_gui.controllers;

import com.example.lab4_gui.DataBean;
import com.example.lab4_gui.math.CalculateData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class ResultController {
    @FXML
    public TableColumn<DataPoint, Double> columnX, columnY, columnYBest, columnEBest;
    @FXML
    public TableColumn<FunctionData, String> columnFunctionName, columnCoeffs, columnMSE;
    @FXML
    private TableView<FunctionData> general_table;
    @FXML
    private TableView<DataPoint> dataTable;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private Text coeff_deter;
    @FXML
    private Text text_coef_deter;
    @FXML
    private Text title_aprox_func;
    @FXML
    private Text val_pirson;

    private double[] xArray;
    private double[] yArray;
    private DataBean data;

    public void setInputData(double[] xArray, double[] yArray) {
        this.xArray = xArray;
        this.yArray = yArray;
    }

    @FXML
    void initialize() {
        // Ничего не делаем в initialize, так как данные могут быть еще не установлены
    }

    public void initializeData() {
        if (xArray == null || yArray == null) {
            throw new IllegalStateException("xArray и yArray должны быть установлены перед инициализацией данных.");
        }

        data = CalculateData.apply(xArray, yArray);

        // Установить значения для колонок general_table
        columnFunctionName.setCellValueFactory(new PropertyValueFactory<>("functionName"));
        columnCoeffs.setCellValueFactory(new PropertyValueFactory<>("coeffs"));
        columnMSE.setCellValueFactory(new PropertyValueFactory<>("mse"));

        // Заполнение general_table
        general_table.getItems().clear();
        general_table.getItems().addAll(
                new FunctionData("Линейная", data.getCoeffsLinear(), data.getMseLinear()),
                new FunctionData("Квадратичная", data.getQuadCoeffs(), data.getMseQuad()),
                new FunctionData("Кубическая", data.getCubicCoeffs(), data.getMseCubic()),
                new FunctionData("Экспоненциальная", data.getExpCoeffs(), data.getMseExp()),
                new FunctionData("Логарифмическая", data.getLogCoeffs(), data.getMseLog()),
                new FunctionData("Степенная", data.getPowerCoeffs(), data.getMsePower())
        );

        // Установить значения для колонок dataTable
        columnX.setCellValueFactory(new PropertyValueFactory<>("x"));
        columnY.setCellValueFactory(new PropertyValueFactory<>("y"));
        columnYBest.setCellValueFactory(new PropertyValueFactory<>("yBest"));
        columnEBest.setCellValueFactory(new PropertyValueFactory<>("eBest"));

        // Заполнение dataTable
        dataTable.getItems().clear();
        for (int i = 0; i < xArray.length; i++) {
            dataTable.getItems().add(new DataPoint(xArray[i], yArray[i], data.getYBest()[i], data.getEBest()[i]));
        }

        // Заполнение графика lineChart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < xArray.length; i++) {
            series.getData().add(new XYChart.Data<>(xArray[i], data.getYBest()[i]));
        }
        lineChart.getData().clear();
        lineChart.getData().add(series);

        // Заполнение текстовых полей
        coeff_deter.setText(String.format("%.3f", data.getR2Linear()));
        text_coef_deter.setText(data.getTextR2());
        title_aprox_func.setText(data.getFunctionBest());
        val_pirson.setText(String.format("%.3f", data.getRPearsonLinear()));
    }

    public static class FunctionData {
        private final String functionName;
        private final double[] coeffs;
        private final double mse;

        public FunctionData(String functionName, double[] coeffs, double mse) {
            this.functionName = functionName;
            this.coeffs = coeffs;
            this.mse = mse;
        }

        public String getFunctionName() {
            return functionName;
        }

        public String getCoeffs() {
            if (coeffs == null) {
                return "";
            }
            return Arrays.toString(coeffs);
        }

        public double getMse() {
            return mse;
        }
    }

    public static class DataPoint {
        private final double x;
        private final double y;
        private final double yBest;
        private final double eBest;

        public DataPoint(double x, double y, double yBest, double eBest) {
            this.x = x;
            this.y = y;
            this.yBest = yBest;
            this.eBest = eBest;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getYBest() {
            return yBest;
        }

        public double getEBest() {
            return eBest;
        }
    }

    @FXML
    private void handleReturn(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/lab4_gui/views/index-view.fxml");
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
