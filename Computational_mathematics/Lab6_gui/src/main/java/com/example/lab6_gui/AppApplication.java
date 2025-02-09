package com.example.lab6_gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppApplication extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppApplication.class.getResource("/com/example/lab6_gui/views/index-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1303.0, 870.0);
        stage.setTitle("Дифференцирование функции");
        stage.setScene(scene);
        stage.show();
    }
}