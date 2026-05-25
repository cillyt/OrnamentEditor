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
import java.util.ArrayList;
import java.util.List;

import static ua.university.ornamenteditor.CanvasPrinting.*;

public class MainScreenController {
//    @FXML
//    private Label welcomeText;
    public int symStatus = 0; //1- вертикальна    2-горизонтальна
 //   public int dupStatus = 0; //1- вертикальна    2-горизонтальна
 public static List <Cross> newOrnament = new ArrayList<>();


    @FXML
    protected void cleanCanvas() {
        System.out.println("cleanCanvas");
        CanvasPrinting.deleteAll();
        redrawGrid();
    }


    @FXML
    protected void verticalDuplication() {
        System.out.println("verticalDuplication");
        int currentCells = mySpinner.getValue();
        CanvasPrinting.verticalDuplication(myCanvas, currentCells);
        redrawGrid();
    }

    @FXML
    protected void horizontalDuplication() {
        System.out.println("horizontalDuplication");
        int currentCells = mySpinner.getValue();
        CanvasPrinting.horizontalDuplication(myCanvas, currentCells);
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

        SpinnerValueFactory<Integer> cells = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 21, 1);
        mySpinner.setValueFactory(cells);

        ornament.clear();
        ornament.addAll(getInitialOrnament());

        mySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            redrawGrid();
        });


        myCanvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                redrawGrid();
            }
        });

        myCanvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                redrawGrid();
            }
        });

        if (myCanvas.getWidth() > 0) {
            redrawGrid();
        }

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
        newOrnament.clear();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        double width = myCanvas.getWidth();
        double height = myCanvas.getHeight();
        //GraphicsContext gc = myCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        double spinnerValue = width / mySpinner.getValue();

        drawGrid(gc, width, height, spinnerValue);

        for(Cross c:ornament){
            Color color = c.getColor();


            double newScale = width / mySpinner.getValue();

            long colIndex = Math.round(c.getX() / c.getOneCellScale());
            long rowIndex = Math.round(c.getY() / c.getOneCellScale());

            double newX = colIndex * newScale;
            double newY = rowIndex * newScale;

// ==========================================
            // МАГІЯ ТУТ: Оновлюємо сам хрестик у пам'яті!
            // Тепер він "знає", що в нього новий розмір і нові координати.
            // Завдяки цьому deleteCross знайде його з першого кліку!
            // ==========================================
            c.setX(newX);
            c.setY(newY);
            c.setOneCellScale(newScale);



            gc.setFill(color);
            gc.setFont(new Font(newScale + 4));
            gc.fillText(cross, newX, newY + newScale - 2);

            newOrnament.add(c);
        }
        ornament.clear();
        ornament.addAll(newOrnament);

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
            gc.setFont(new Font(c.getOneCellScale()+4));
            gc.fillText(cross, c.getX(), c.getY() + c.getOneCellScale() - 2);
        }


    }

    public void drawName() {
        deleteAll();



        double width = myCanvas.getWidth();
        double height = myCanvas.getHeight();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();


        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        drawGrid(gc, width, height, (double) 500 /21);

        for(Cross c: getInitialOrnament()){
            gc.setFill(c.getColor());
            gc.setFont(new Font(c.getOneCellScale() + 4));
            gc.fillText(cross, c.getX(), c.getY() + c.getOneCellScale() - 2);
            ornament.add(c);
        }
    }


    public static List<Cross> getInitialOrnament() {
        List<Cross> ornamentList = new ArrayList<>();

        ornamentList.add(new Cross(238.0952380952381, 47.61904761904765, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 428.57142857142856, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(214.28571428571428, 404.76190476190476, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(214.28571428571428, 71.42857142857143, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 404.76190476190476, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 71.42857142857143, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 404.76190476190476, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 71.42857142857143, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 380.95238095238096, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 95.23809523809524, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 380.95238095238096, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 95.23809523809524, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(47.61904761904762, 238.0952380952381, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(71.42857142857143, 214.28571428571428, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(71.42857142857143, 238.0952380952381, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(71.42857142857143, 261.9047619047619, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 190.47619047619048, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 285.7142857142857, Color.web("#000000"), 23.80952380952381));

        ornamentList.add(new Cross(214.28571428571428, 95.23809523809524, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(214.28571428571428, 380.95238095238096, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 95.23809523809524, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 380.95238095238096, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 95.23809523809524, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 380.95238095238096, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 95.23809523809524, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 380.95238095238096, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 95.23809523809524, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 380.95238095238096, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 95.23809523809524, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 380.95238095238096, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 95.23809523809524, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 380.95238095238096, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 119.04761904761904, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 357.14285714285717, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 142.85714285714283, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(238.0952380952381, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 142.85714285714283, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 142.85714285714283, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(214.28571428571428, 142.85714285714283, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(214.28571428571428, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 142.85714285714283, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 357.14285714285717, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 357.14285714285717, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 357.14285714285717, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 357.14285714285717, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(380.95238095238096, 214.28571428571428, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 214.28571428571428, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 238.0952380952381, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(380.95238095238096, 261.9047619047619, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 261.9047619047619, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 166.66666666666669, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(380.95238095238096, 142.85714285714286, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 142.85714285714286, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 309.5238095238095, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(380.95238095238096, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(95.23809523809524, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(357.14285714285717, 238.0952380952381, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(119.04761904761905, 238.0952380952381, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 238.0952380952381, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 238.0952380952381, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 214.28571428571428, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 214.28571428571428, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(357.14285714285717, 190.47619047619048, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(119.04761904761905, 190.47619047619048, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 190.47619047619048, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 190.47619047619048, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(357.14285714285717, 285.7142857142857, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(119.04761904761905, 285.7142857142857, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 285.7142857142857, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 285.7142857142857, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 261.9047619047619, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 261.9047619047619, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(357.14285714285717, 166.66666666666669, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(119.04761904761905, 166.66666666666669, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(357.14285714285717, 309.5238095238095, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(119.04761904761905, 309.5238095238095, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 142.85714285714286, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 142.85714285714286, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(333.33333333333337, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(142.85714285714286, 333.33333333333337, Color.web("#cc3333"), 23.80952380952381));

        ornamentList.add(new Cross(166.66666666666669, 309.5238095238095, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 166.66666666666669, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 309.5238095238095, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 166.66666666666669, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(214.28571428571428, 309.5238095238095, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(214.28571428571428, 166.66666666666669, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 285.7142857142857, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 190.47619047619048, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 261.9047619047619, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(166.66666666666669, 214.28571428571428, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 285.7142857142857, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 190.47619047619048, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 309.5238095238095, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 166.66666666666669, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 309.5238095238095, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 166.66666666666669, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 309.5238095238095, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(261.9047619047619, 166.66666666666669, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 285.7142857142857, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 190.47619047619048, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 261.9047619047619, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 214.28571428571428, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 285.7142857142857, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 190.47619047619048, Color.web("#000000"), 23.80952380952381));

        ornamentList.add(new Cross(380.95238095238096, 190.47619047619048, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(380.95238095238096, 285.7142857142857, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(404.76190476190476, 214.28571428571428, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(404.76190476190476, 238.0952380952381, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(404.76190476190476, 261.9047619047619, Color.web("#000000"), 23.80952380952381));
        ornamentList.add(new Cross(428.57142857142856, 238.0952380952381, Color.web("#000000"), 23.80952380952381));

        ornamentList.add(new Cross(166.66666666666669, 119.04761904761905, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(190.47619047619048, 119.04761904761905, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(285.7142857142857, 119.04761904761905, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(309.5238095238095, 119.04761904761905, Color.web("#cc3333"), 23.80952380952381));

        ornamentList.add(new Cross(380.95238095238096, 166.66666666666669, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(380.95238095238096, 238.0952380952381, Color.web("#cc3333"), 23.80952380952381));
        ornamentList.add(new Cross(380.95238095238096, 309.5238095238095, Color.web("#cc3333"), 23.80952380952381));

        return ornamentList;
    }

}
