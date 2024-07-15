package com.example.lab5_gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(AppApplication.class.getResource("/com/example/lab5_gui/views/index-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1303.0, 870.0);
        stage.setTitle("Интерполяция функции");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}