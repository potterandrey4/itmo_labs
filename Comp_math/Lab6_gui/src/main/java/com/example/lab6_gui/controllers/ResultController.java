package com.example.lab6_gui.controllers;

import com.example.lab6_gui.beans.DataBean;
import com.example.lab6_gui.beans.TableRowData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ResultController {
    @FXML
    public Text e_euler, e_impr_euler;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private TableView<TableRowData> tableView;
    @FXML
    private TableColumn<TableRowData, Double> xColumn;
    @FXML
    private TableColumn<TableRowData, Double> yExactColumn;
    @FXML
    private TableColumn<TableRowData, Double> yEulerColumn;
    @FXML
    private TableColumn<TableRowData, Double> yImprovedEulerColumn;
    @FXML
    private TableColumn<TableRowData, Double> yAdamsColumn;
    DataBean result;

    public void setInputData(DataBean result) {
        this.result = result;

        e_euler.setText("Точность метода Эйлера по правилу Рунге: " + result.getEpsilon());
        e_impr_euler.setText("Точность усоверш. метода Эйлера по правилу Рунге: " + result.getEpsilon());

        updateChart();
        updateTable();
    }

    private void updateChart() {
        lineChart.getData().clear();

        XYChart.Series<Number, Number> exactSeries = new XYChart.Series<>();
        exactSeries.setName("Exact Solution");

        XYChart.Series<Number, Number> eulerSeries = new XYChart.Series<>();
        eulerSeries.setName("Euler");

        XYChart.Series<Number, Number> improvedEulerSeries = new XYChart.Series<>();
        improvedEulerSeries.setName("Improved Euler");

        XYChart.Series<Number, Number> adamsSeries = new XYChart.Series<>();
        adamsSeries.setName("Adams");

        for (int i = 0; i < result.getXExact().length; i++) {
            exactSeries.getData().add(new XYChart.Data<>(result.getXExact()[i], result.getYExact()[i]));
            eulerSeries.getData().add(new XYChart.Data<>(result.getXExact()[i], result.getYEuler()[i]));
            improvedEulerSeries.getData().add(new XYChart.Data<>(result.getXExact()[i], result.getYImprovedEuler()[i]));
            adamsSeries.getData().add(new XYChart.Data<>(result.getXExact()[i], result.getYAdams()[i]));
        }

        lineChart.getData().addAll(exactSeries, eulerSeries, improvedEulerSeries, adamsSeries);
    }

    private void updateTable() {
        ObservableList<TableRowData> data = FXCollections.observableArrayList();

        for (int i = 0; i < result.getXExact().length; i++) {
            data.add(new TableRowData(
                    result.getXExact()[i],
                    result.getYExact()[i],
                    result.getYEuler()[i],
                    result.getYImprovedEuler()[i],
                    result.getYAdams()[i]
            ));
        }

        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yExactColumn.setCellValueFactory(new PropertyValueFactory<>("yExact"));
        yEulerColumn.setCellValueFactory(new PropertyValueFactory<>("yEuler"));
        yImprovedEulerColumn.setCellValueFactory(new PropertyValueFactory<>("yImprovedEuler"));
        yAdamsColumn.setCellValueFactory(new PropertyValueFactory<>("yAdams"));

        tableView.setItems(data);
    }


    @FXML
    public void handleReturn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab6_gui/views/index-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
