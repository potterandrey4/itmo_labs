module com.example.lab4_gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.lab4_gui to javafx.graphics;
    opens com.example.lab4_gui.controllers to javafx.fxml;
    exports com.example.lab4_gui;
}
