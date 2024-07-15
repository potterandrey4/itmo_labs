package com.example.lab5_gui.controllers;

import com.example.lab5_gui.beans.DataPointBean;
import com.example.lab5_gui.beans.DataBean;
import com.example.lab5_gui.math.CalculateData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;

import static com.example.lab5_gui.math.InterpolationMethods.*;

public class ResultController {
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private Text root_Lagrange, root_Newton_sep , root_Newton_end;
    private double[] xArray;
    private double[] yArray;
    private double function_arg, nodes_interpolation;
    private DataBean data;

    DecimalFormat df = new DecimalFormat("###.##########");

    public void setInputData(double[] xArray, double[] yArray, double function_arg, double nodes_interpolation) {
        this.xArray = xArray;
        this.yArray = yArray;
        this.function_arg = function_arg;
        this.nodes_interpolation = nodes_interpolation;
        initializeData();
    }

    public void initializeData() {
        data = CalculateData.apply(xArray, yArray, function_arg);
        lineChart.getData().clear();

        // Заполнение текстовых полей
        root_Lagrange.setText( df.format(data.getRootLagrange()) );
        root_Newton_sep.setText( df.format(data.getRootNewtonSep()) );
        root_Newton_end.setText( df.format(data.getRootNewtonEnd()) );

//        double[] yLagrange = new double[24];
        double[] yNewtonSep = new double[24];
        double[] yNewtonEnd = new double[24];

        for (double i = 0; i < 12; i+=0.5) {
//            yLagrange[(int) (i*2)] = methodLagrange(xArray, yArray, i);
            yNewtonSep[(int) (i*2)] = methodNewtonSep(xArray, yArray, i);
            yNewtonEnd[(int) (i*2)] = methodNewtonEnd(xArray, yArray, i);
        }

        addSeries("Исходная функция", xArray, yArray);
        addSeries("Метод Ньютона с разделёнными разностями", xArray, yNewtonSep);
        addSeries("Метод Ньютона с конечными разностями", xArray, yNewtonEnd);
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
        switchScene(actionEvent, "/com/example/lab5_gui/views/index-view.fxml");
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
