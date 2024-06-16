package com.example.lab2_gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;

public class ResultEquationController {
	@FXML
	private Text dih_x;
	@FXML
	private Text dih_f;
	@FXML
	private Text dih_iter;
	@FXML
	private Text newt_x;
	@FXML
	private Text newt_f;
	@FXML
	private Text newt_iter;
	@FXML
	private Text simple_x;
	@FXML
	private Text simple_f;
	@FXML
	private Text simple_iter;

	DecimalFormat df = new DecimalFormat("###.#########");

	public void setResultData(double[] bisectionMethodRoot, double[] newtonMethonRoot, double[] simpleIterRoot) {
		dih_x.setText("x: " + df.format(bisectionMethodRoot[0]));
		dih_f.setText("f(x): " + df.format(bisectionMethodRoot[1]));
		dih_iter.setText("Итерации: " + bisectionMethodRoot[2]);

		newt_x.setText("x: " + df.format(newtonMethonRoot[0]));
		newt_f.setText("f(x): " + df.format(newtonMethonRoot[1]));
		newt_iter.setText("Итерации: " + newtonMethonRoot[2]);

		simple_x.setText("x: " + df.format(simpleIterRoot[0]));
		simple_f.setText("f(x): " + df.format(simpleIterRoot[1]));
		simple_iter.setText("Итерации: " + simpleIterRoot[2]);
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
