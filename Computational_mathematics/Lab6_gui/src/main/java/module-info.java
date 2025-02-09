module com.example.lab6_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.example.lab6_gui to javafx.graphics;
    opens com.example.lab6_gui.controllers to javafx.fxml;
    exports com.example.lab6_gui;
    exports com.example.lab6_gui.beans;
    opens com.example.lab6_gui.beans to javafx.graphics;
}
