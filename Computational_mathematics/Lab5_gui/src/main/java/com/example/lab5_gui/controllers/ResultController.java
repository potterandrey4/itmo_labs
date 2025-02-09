package com.example.lab5_gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;

import static com.example.lab5_gui.math.InterpolationMethods.*;

public class ResultController {
    DecimalFormat df = new DecimalFormat("###.##########");
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private Text root_x, fact_x, root_Lagrange, root_Newton_sep, root_Newton_end;
    private double[] xArray, yArray;    // исходные данные функции
    private double[] rx, ry;            // данные интерполяции
    private double function_arg, f_x;


    public void setInputData(double[] xArray, double[] yArray, double function_arg, double f_x, int nodes_interpolation, double[] rx, double[] ry) {
        this.xArray = xArray;
        this.yArray = yArray;
        this.function_arg = function_arg;
        this.f_x = f_x;
        this.rx = rx;
        this.ry = ry;
        root_x.setText("Интерполяция функции в точке x = " + function_arg + " для " + nodes_interpolation + " узлов");
        initializeData();
    }

    public void initializeData() {

        lineChart.getData().clear();

        // Заполнение текстовых полей
        fact_x.setText("Фактическое значение: f(x) = " + df.format(f_x));
        root_Lagrange.setText(df.format(methodLagrange(rx, ry, function_arg)));
        root_Newton_sep.setText(df.format(methodNewtonSep(rx, ry, function_arg)));
        root_Newton_end.setText(df.format(methodNewtonEnd(rx, ry, function_arg)));

        double[] yNewtonSep = new double[12];
        double[] yNewtonEnd = new double[12];
        for (int i = 0; i < 12; i++) {
            yNewtonSep[i] = methodNewtonSep(rx, ry, i+1);
            yNewtonEnd[i] = methodNewtonEnd(rx, ry, i+1);
        }

        addSeries("Исходная функция", xArray, yArray);
        addSeries("Метод Ньютона с разделёнными разностями", xArray, yNewtonSep);
        addSeries("Метод Ньютона с конечными разностями", xArray, yNewtonEnd);
        addSeries("Узлы интерполяции", rx, ry);
    }

    private void addSeries(String name, double[] x, double[] y) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        for (int i = 0; i < x.length; i++) {
            series.getData().add(new XYChart.Data<>(x[i], y[i]));
        }
        lineChart.getData().add(series);
        if ("Узлы интерполяции".equals(name)) {
            // код, убирающий линию, соединяющую точки
            series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: null;");
        } else {
            for (XYChart.Data<Number, Number> data : series.getData()) {
                data.getNode().setVisible(false);
            }
        }
    }

    @FXML
    public void handleReturn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab5_gui/views/index-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
