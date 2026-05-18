module ua.university.ornamenteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens ua.university.ornamenteditor to javafx.fxml;
    exports ua.university.ornamenteditor;
}