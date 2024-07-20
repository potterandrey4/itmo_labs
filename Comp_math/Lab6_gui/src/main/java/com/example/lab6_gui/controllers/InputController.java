package com.example.lab6_gui.controllers;

import com.example.lab6_gui.beans.DataBean;
import com.example.lab6_gui.math.CalculateData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InputController {

    String fxId = "";
    @FXML
    private TextField x0_input, y0_input, xn_input, h_input, e_input;
    @FXML
    private Button equation1, equation2, equation3;

    public void handleCalculate(ActionEvent event) {
        double x0 = Double.parseDouble(x0_input.getText().replace(",", "."));
        double y0 = Double.parseDouble(y0_input.getText().replace(",", "."));
        double xn = Double.parseDouble(xn_input.getText().replace(",", "."));

        double h = Double.parseDouble(h_input.getText().replace(",", "."));
//        int h = Integer.parseInt(h_input.getText());
        double e = Double.parseDouble(e_input.getText().replace(",", "."));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab6_gui/views/result-view.fxml"));
            Parent root = loader.load();

            ResultController resultController = loader.getController();
            DataBean result = CalculateData.apply(fxId, x0, y0, xn, e, h);
            resultController.setInputData(result);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    @FXML
    private void setFxId(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        fxId = clickedButton.getId();
        equation1.setStyle("");
        equation2.setStyle("");
        equation3.setStyle("");
        clickedButton.setStyle(
                "-fx-border-width: 3 ; \n" +
                        "-fx-border-color: #5e9bff;"
        );
    }
}
