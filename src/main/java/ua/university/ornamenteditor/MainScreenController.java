package ua.university.ornamenteditor;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MainScreenController {
//    @FXML
//    private Label welcomeText;

    @FXML
    protected void cleanCanvas() {
        System.out.println("cleanCanvas");
        CanvasPrinting.deleteAll(myCanvas);
    }


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

        // Дозволяємо вибирати тільки картинки
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Зображення PNG (*.png)", "*.png")
        );

        File file = fileChooser.showOpenDialog(myCanvas.getScene().getWindow());

        // Якщо користувач вибрав файл (не натиснув "Скасувати"):
        if (file != null) {
            // 2. Завантажуємо файл як об'єкт Image
            // Метод toURI().toString() перетворює шлях до файлу у зрозумілий для JavaFX формат
            Image image = new Image(file.toURI().toString());

            // 3. Беремо пензлик нашого полотна
            GraphicsContext gc = myCanvas.getGraphicsContext2D();

            // (За бажанням) Очищаємо полотно перед тим, як вставити нову картинку
            gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

            // 4. Малюємо картинку на полотні!
            // Координати (0, 0) - це лівий верхній кут. Далі вказуємо ширину і висоту полотна.
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

}
