package ua.university.ornamenteditor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static ua.university.ornamenteditor.CanvasPrinting.cross;
import static ua.university.ornamenteditor.CanvasPrinting.ornament;

public class MainScreenController {
//    @FXML
//    private Label welcomeText;
    public int symStatus = 0; //1- вертикальна    2-горизонтальна
    public int dupStatus = 0; //1- вертикальна    2-горизонтальна

    @FXML
    protected void cleanCanvas() {
        System.out.println("cleanCanvas");
        CanvasPrinting.deleteAll();
        redrawGrid();
    }


    @FXML
    protected void changeCross(MouseEvent e) {
        int spinnerVal = mySpinner.getValue();
        if (clearOne.isSelected()) {
            System.out.println("cleanOneCross");
            CanvasPrinting.deleteCross(e,500.0 / spinnerVal, 500.0 / spinnerVal);
            redrawGridWithOrnament();
        }
        else if(!clearOne.isSelected()) {
            System.out.println("printCross");
            Color picked = pickedColor.getValue();
            if(chooseSymetry.getValue().equals("Немає")){
                System.out.println("Немає");
                symStatus = 0;
            }
            if(chooseSymetry.getValue().equals("Вертикальна")){
                System.out.println("Вертикальна");
                symStatus = 1;
            }
            else if(chooseSymetry.getValue().equals("Горизонтальна")){
                System.out.println("Горизонтальна");
                symStatus = 2;
            }
            CanvasPrinting.putCross(e, myCanvas, picked, symStatus,500.0 / spinnerVal, 500.0 / spinnerVal);
        }
    }

    @FXML
    public void initialize() {
        pickedColor.setValue(Color.web("#990000"));

        SpinnerValueFactory<Integer> cells = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 20, 1);

        mySpinner.setValueFactory(cells);

        mySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            redrawGrid();
        });

        double width = myCanvas.getWidth();
        double height = myCanvas.getHeight();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        double cells1 =  width / (mySpinner.getValue());


        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        drawGrid(gc, width, height, cells1);

    }

//    @FXML
//    protected void chooseSymetry() {
//        if(chooseSymetry.getValue().equals("Вертикальна")){
//            System.out.println("Вертикальна");
//        }
//    }

//    @FXML
//    protected void chooseDuplication() {
//        System.out.println("chooseDuplication");
//    }



    @FXML
    public void saveOrnament(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Збереження орнаменту");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Зображення PNG (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(myCanvas.getScene().getWindow());

        if (file != null) {
            try {
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                WritableImage snapshot = myCanvas.snapshot(params, null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                System.out.println("Ornament is saved as: " + file.getAbsolutePath());
            }
            catch (IOException ex) {
                System.out.println("Помилка збереження: " + ex.getMessage());
            }
        }
    }




    @FXML
    protected void openOrnament() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Відкрити орнамент");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Зображення PNG (*.png)", "*.png")
        );

        File file = fileChooser.showOpenDialog(myCanvas.getScene().getWindow());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            GraphicsContext gc = myCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
            gc.drawImage(image, 0, 0, myCanvas.getWidth(), myCanvas.getHeight());
            System.out.println("Орнамент успішно завантажено!");
        }
    }


    @FXML
    protected ColorPicker pickedColor;

    @FXML
    protected RadioButton clearOne;

    @FXML
    protected ChoiceBox chooseSymetry;

    @FXML
    private Canvas myCanvas;

    @FXML
    private Spinner<Integer> mySpinner;




    private void drawGrid(GraphicsContext gc, double width, double height, double cellSize) {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1.0);

        for (double x = 0; x <= width; x += cellSize) {
            gc.strokeLine(x + 0.5, 0, x + 0.5, height);
        }

        for (double y = 0; y <= height; y += cellSize) {
            gc.strokeLine(0, y + 0.5, width, y + 0.5);
        }
    }





    @FXML
    public void redrawGrid() {
        ornament.clear();

        double width = myCanvas.getWidth();
        double height = myCanvas.getHeight();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        double spinnerValue = width / mySpinner.getValue();

        drawGrid(gc, width, height, spinnerValue);

    }

    @FXML
    public void redrawGridWithOrnament() {
//        ornament.clear();

        double width = myCanvas.getWidth();
        double height = myCanvas.getHeight();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        double spinnerValue = width / mySpinner.getValue();

        drawGrid(gc, width, height, spinnerValue);

        for(Cross c:ornament){
            gc.setFill(c.getColor());
            gc.setFont(new Font(c.getOneCellScale()));
            gc.fillText(cross, c.getX(), c.getY() + c.getOneCellScale() - 2);
        }


    }


    @FXML
    public void drawName() {

        double width = myCanvas.getWidth();
        double height = myCanvas.getHeight();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        double spinnerValue = width / mySpinner.getValue();

        drawGrid(gc, width, height, spinnerValue);

        for(Cross c:ornament){
            gc.setFill(c.getColor());
            gc.setFont(new Font(c.getOneCellScale()));
            gc.fillText(cross, c.getX(), c.getY() + c.getOneCellScale() - 2);
        }

    }

}
