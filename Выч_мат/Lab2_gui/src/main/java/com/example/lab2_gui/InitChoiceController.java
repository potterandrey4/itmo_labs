package com.example.lab2_gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InitChoiceController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button choice1;

	@FXML
	private Button choice2;

	@FXML
	void initialize() {
		assert choice1 != null : "fx:id=\"choice1\" was not injected: check your FXML file 'index-view.fxml'.";
		assert choice2 != null : "fx:id=\"choice2\" was not injected: check your FXML file 'index-view.fxml'.";

	}

}
