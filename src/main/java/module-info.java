module ua.university.ornamenteditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens ua.university.ornamenteditor to javafx.fxml;
    exports ua.university.ornamenteditor;
}