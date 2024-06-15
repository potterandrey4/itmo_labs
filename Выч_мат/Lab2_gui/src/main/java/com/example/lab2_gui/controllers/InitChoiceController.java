package com.example.lab2_gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InitChoiceController {

	@FXML
	private void handleChoice1(ActionEvent actionEvent) {
		switchScene(actionEvent, "/com/example/lab2_gui/views/equation-view.fxml");
	}

	@FXML
	private void handleChoice2(ActionEvent actionEvent) {
		switchScene(actionEvent, "/com/example/lab2_gui/views/systemEquations-view.fxml");
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
