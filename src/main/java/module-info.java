module ua.university.ornamenteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;


    opens ua.university.ornamenteditor to javafx.fxml;
    exports ua.university.ornamenteditor;
}