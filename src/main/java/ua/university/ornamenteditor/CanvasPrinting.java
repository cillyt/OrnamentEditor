package ua.university.ornamenteditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class CanvasPrinting {
    public static String cross = "✖";
    public static List <Cross> ornament = new ArrayList();

    private static double gridWidth = 470.0 / 26.0;
    private static double gridHeight = 470.0 / 26.0;

    public static void putCross(MouseEvent e, Canvas canvas, Color pickedColor, int symStatus) {
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

        if (symStatus == 1) {               //vertical
            gc.fillText(cross, (470 - ((double) 470 /26)) - newX, newY + gridHeight - 2);

            Cross c = new Cross((470 - ((double) 470 /26)) - newX, newY, pickedColor);
            ornament.removeIf(cr -> cr.getX() == (470 - ((double) 470 /26)) - newX && cr.getY() == newY);
            ornament.add(c);
        }

        else if (symStatus == 2) {          //horizontal
            gc.fillText(cross, newX, (470 - ((double) 470 /26)) - newY + gridHeight - 2);

            Cross c = new Cross(newX, (470 - ((double) 470 /26)) - newY, pickedColor);
            ornament.removeIf(cr -> cr.getX() == newX && cr.getY() == (470 - ((double) 470 /26)) - newY);
            ornament.add(c);
        }



        Cross c = new Cross(newX, newY, pickedColor);
        ornament.removeIf(cr -> cr.getX() == newX && cr.getY() == newY);
        ornament.add(c);

        for(Cross cr : ornament){           //видалити після тестувань
            System.out.println(cr);
        }
    }

    public static void deleteCross(MouseEvent e, Canvas canvas) {
        double x = e.getX();
        double y = e.getY();

        double newX = Math.floor(x / gridWidth) * gridWidth;
        double newY = Math.floor(y / gridHeight) * gridHeight;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(newX, newY, gridWidth, gridHeight);

        ornament.removeIf((Cross cr) -> cr.getX() == newX && cr.getY() == newY);
    }

    public static void deleteAll(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 470, 470);
        ornament.clear();
    }

}