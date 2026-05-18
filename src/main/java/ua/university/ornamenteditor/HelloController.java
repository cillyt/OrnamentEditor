package ua.university.ornamenteditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
//    @FXML
//    private Label welcomeText;

    @FXML
    protected void onStartButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-screen.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Помилка завантаження екрана: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
    @FXML
    protected void onNewButtonClick() {}
    @FXML
    protected void onNewGenerateButtonClick() {}
    @FXML
    protected void onSaveButtonClick() {}
    @FXML
    protected void onOpenButtonClick() {}
    @FXML
    protected void onInstructionButtonClick() {}

}
