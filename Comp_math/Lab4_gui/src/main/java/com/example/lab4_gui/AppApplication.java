package com.example.lab4_gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppApplication.class.getResource("/com/example/lab4_gui/views/index-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1303.0, 870.0);
        stage.setTitle("Аппроксимация функции методом наименьших квадратов");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}