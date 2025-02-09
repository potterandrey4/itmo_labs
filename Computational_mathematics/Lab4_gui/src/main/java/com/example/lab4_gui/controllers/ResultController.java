package com.example.lab4_gui.controllers;

import com.example.lab4_gui.beans.DataPointBean;
import com.example.lab4_gui.beans.DataBean;
import com.example.lab4_gui.beans.FunctionBean;
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

import static java.lang.Double.NaN;

public class ResultController {
    @FXML
    private TableView<FunctionBean> general_table;
    @FXML
    public TableColumn<FunctionBean, String> columnFunctionName, a, b, c, d, columnMSE;
    @FXML
    private TableView<DataPointBean> best_table;
    @FXML
    public TableColumn<DataPointBean, String> columnX, columnY, columnYBest, columnEBest;
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

    public void initializeData() {
        data = CalculateData.apply(xArray, yArray);
        lineChart.getData().clear();

        // Установить значения для колонок general_table
        columnFunctionName.setCellValueFactory(new PropertyValueFactory<>("functionName"));
        a.setCellValueFactory(new PropertyValueFactory<>("a"));
        b.setCellValueFactory(new PropertyValueFactory<>("b"));
        c.setCellValueFactory(new PropertyValueFactory<>("c"));
        d.setCellValueFactory(new PropertyValueFactory<>("d"));
        columnMSE.setCellValueFactory(new PropertyValueFactory<>("mse"));

        // Заполнение general_table
        general_table.getItems().clear();
        general_table.getItems().addAll(
                new FunctionBean("Линейная", data.getCoeffsLinear(), data.getMseLinear()),
                new FunctionBean("Квадратичная", data.getQuadCoeffs(), data.getMseQuad()),
                new FunctionBean("Кубическая", data.getCubicCoeffs(), data.getMseCubic()),
                new FunctionBean("Экспоненциальная", data.getExpCoeffs(), data.getMseExp()),
                new FunctionBean("Логарифмическая", data.getLogCoeffs(), data.getMseLog()),
                new FunctionBean("Степенная", data.getPowerCoeffs(), data.getMsePower())
        );

        // Установить значения для колонок dataTable
        columnX.setCellValueFactory(new PropertyValueFactory<>("x"));
        columnY.setCellValueFactory(new PropertyValueFactory<>("y"));
        columnYBest.setCellValueFactory(new PropertyValueFactory<>("yBest"));
        columnEBest.setCellValueFactory(new PropertyValueFactory<>("eBest"));

        // Заполнение dataTable
        best_table.getItems().clear();
        for (int i = 0; i < xArray.length; i++) {
            best_table.getItems().add(new DataPointBean(xArray[i], yArray[i], data.getYBest()[i], data.getEBest()[i]));
        }

        // Заполнение текстовых полей
        coeff_deter.setText(String.format("%.3f", data.getR2Linear()));
        text_coef_deter.setText(data.getTextR2());
        title_aprox_func.setText(data.getFunctionBest());
        val_pirson.setText(String.format("%.3f", data.getRPearsonLinear()));

        // Заполнение графика lineChart
//        addSeries("Лучшая функция", xArray, data.getYBest());
//        addSeries("Линейная функция", xArray, data.getYLinear());
//        addSeries("Квадратичная функция", xArray, data.getYQuad());
//        addSeries("Кубическая функция", xArray, data.getYCubic());
//        addSeries("Экспоненциальная функция", xArray, data.getYExp());
//        addSeries("Логарифмическая функция", xArray, data.getYLog());
//        addSeries("Степенная функция", xArray, data.getYPower());

        String bestFunctionName = data.getFunctionBest();
        switch (bestFunctionName) {
            case "Линейная аппроксимация":
                addSeries("Лучшая (Линейная) функция", xArray, data.getYLinear());
                break;
            case "Квадратичная аппроксимация":
                addSeries("Лучшая (Квадратичная) функция", xArray, data.getYQuad());
                break;
            case "Кубическая аппроксимация":
                addSeries("Лучшая (Кубическая) функция", xArray, data.getYCubic());
                break;
            case "Экспоненциальная аппроксимация":
                addSeries("Лучшая (Экспоненциальная) функция", xArray, data.getYExp());
                break;
            case "Логарифмическая аппроксимация":
                addSeries("Лучшая (Логарифмическая) функция", xArray, data.getYLog());
                break;
            case "Степенная аппроксимация":
                addSeries("Лучшая (Степенная) функция", xArray, data.getYPower());
                break;
        }

// Добавление остальных функций на график, исключая лучшую
        if (!bestFunctionName.equals("Линейная аппроксимация")) {
            addSeries("Линейная функция", xArray, data.getYLinear());
        }
        if (!bestFunctionName.equals("Квадратичная аппроксимация")) {
            addSeries("Квадратичная функция", xArray, data.getYQuad());
        }
        if (!bestFunctionName.equals("Кубическая аппроксимация")) {
            addSeries("Кубическая функция", xArray, data.getYCubic());
        }
        if (!bestFunctionName.equals("Экспоненциальная аппроксимация")) {
            addSeries("Экспоненциальная функция", xArray, data.getYExp());
        }
        if (!bestFunctionName.equals("Логарифмическая аппроксимация")) {
            addSeries("Логарифмическая функция", xArray, data.getYLog());
        }
        if (!bestFunctionName.equals("Степенная аппроксимация")) {
            addSeries("Степенная функция", xArray, data.getYPower());
        }

        addSeries("Исходные данные", xArray, yArray);

    }

    private void addSeries(String name, double[] x, double[] y) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        for (int i = 0; i < x.length; i++) {
            series.getData().add(new XYChart.Data<>(x[i], y[i]));
        }
        lineChart.getData().add(series);
        if (!name.equals("Исходные данные")) {
            for (XYChart.Data<Number, Number> data : series.getData()) {
                data.getNode().setVisible(false);
            }
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
