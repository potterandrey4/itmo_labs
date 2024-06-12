module com.example.lab2_gui {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.lab2_gui to javafx.fxml;
	exports com.example.lab2_gui;
}