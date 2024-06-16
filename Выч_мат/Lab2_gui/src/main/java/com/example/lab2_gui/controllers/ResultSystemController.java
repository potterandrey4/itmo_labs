package com.example.lab2_gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultSystemController {
	@FXML
	private TextArea x1_val;
	@FXML
	private TextArea x2_val;
	@FXML
	private TextArea x1_eps;
	@FXML
	private TextArea x2_eps;
	@FXML
	private Text iter;

	public void setResultData(double x1Val, double x2Val, double x1Eps, double x2Eps, double iterations) {
		x1_val.setText(String.valueOf(x1Val));
		x2_val.setText(String.valueOf(x2Val));
		x1_eps.setText(String.valueOf(x1Eps));
		x2_eps.setText(String.valueOf(x2Eps));
		iter.setText(String.valueOf(iterations));
	}

	@FXML
	private void handleReturn(ActionEvent actionEvent) {
		switchScene(actionEvent, "/com/example/lab2_gui/views/index-view.fxml");
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
