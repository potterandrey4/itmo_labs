module com.example.lab4_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.example.lab4_gui to javafx.graphics;
    opens com.example.lab4_gui.controllers to javafx.fxml;
    exports com.example.lab4_gui;
    exports com.example.lab4_gui.beans;
    opens com.example.lab4_gui.beans to javafx.graphics;
}
