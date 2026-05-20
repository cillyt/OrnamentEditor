package ua.university.ornamenteditor;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

import javafx.scene.input.MouseEvent;

public class MainScreenController {
//    @FXML
//    private Label welcomeText;

    @FXML
    protected void cleanCanvas() {
        System.out.println("cleanCanvas");
        CanvasPrinting.deleteAll(myCanvas);
    }

//    @FXML
//    protected void cleanOneCross(MouseEvent e) {
//        if (clearOne.isSelected()) {
//            System.out.println("cleanOneCross");
//            CanvasPrinting.deleteCross(e, myCanvas);
//
//        }
//        else
//            System.out.println("dont clear OneCross");
//
//
//    }


    @FXML
    protected void changeCross(MouseEvent e) {
        if (clearOne.isSelected()) {
            System.out.println("cleanOneCross");
            CanvasPrinting.deleteCross(e, myCanvas);
        }
        else if(!clearOne.isSelected()) {
            System.out.println("printCross");
            Color picked = pickedColor.getValue();
            CanvasPrinting.putCross(e, myCanvas, picked);
        }
    }

    @FXML
    public void initialize() {
        pickedColor.setValue(Color.web("#990000"));
    }

    @FXML
    protected void chooseSymetry() {
        if(chooseSymetry.getValue().equals("Вертикальна")){
            System.out.println("Вертикальна");
        }


    }

    @FXML
    protected void chooseDuplication() {
        System.out.println("chooseDuplication");
    }

    @FXML
    protected void printOrnament() {
        System.out.println("printOrnament");
    }
//
//    @FXML
//    protected void printCross(MouseEvent e) {
//        if (!clearOne.isSelected()) {
//            System.out.println("printCross");
//            Color picked = pickedColor.getValue();
//            CanvasPrinting.putCross(e, myCanvas, picked);
//        }
//
//    }

    @FXML
    protected ColorPicker pickedColor;

    @FXML
    protected RadioButton clearOne;

    @FXML
    protected ChoiceBox chooseSymetry;

    @FXML
    private Canvas myCanvas;

}
