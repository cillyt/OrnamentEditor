package ua.university.ornamenteditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CanvasPrinting {
    public static String cross = "✖";

    private static double gridWidth = 470.0 / 27.0;
    private static double gridHeight = 470.0 / 27.0;

    public static void putCross(MouseEvent e, Canvas canvas, Color pickedColor) {
        double x = e.getX();
        double y = e.getY();
//        System.out.println("x=" + x + ", y=" + y);

        double newX = Math.floor(x / gridWidth) * gridWidth;
        double newY = Math.floor(y / gridHeight) * gridHeight;

        GraphicsContext gc = canvas.getGraphicsContext2D();

//        gc.setFill(Color.LIGHTGRAY);
//        gc.fillRect(newX, newY, gridWidth, gridHeight);
        gc.setFill(pickedColor);

        gc.setFont(new Font(19));

        gc.fillText(cross, newX, newY + gridHeight - 2);
    }

    public static void deleteCross(MouseEvent e, Canvas canvas) {
        double x = e.getX();
        double y = e.getY();

        double newX = Math.floor(x / gridWidth) * gridWidth;
        double newY = Math.floor(y / gridHeight) * gridHeight;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(newX, newY, gridWidth, gridHeight);
    }

    public static void deleteAll(Canvas canvas) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 470, 470);
    }

}