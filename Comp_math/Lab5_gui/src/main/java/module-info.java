module com.example.lab5_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.example.lab5_gui to javafx.graphics;
    opens com.example.lab5_gui.controllers to javafx.fxml;
    exports com.example.lab5_gui;
    exports com.example.lab5_gui.beans;
    opens com.example.lab5_gui.beans to javafx.graphics;
}
