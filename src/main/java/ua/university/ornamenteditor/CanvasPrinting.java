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
    public static List <Cross> ornament = new ArrayList<>();
    //public static List <Cross> name = new ArrayList<>(List.of());



    public static void putCross(MouseEvent e, Canvas canvas, Color pickedColor, int symStatus, double gridWidth, double gridHeight) {
        double x = e.getX();
        double y = e.getY();
//        System.out.println("x=" + x + ", y=" + y);
//grid... - розмір однієї комірочки
        double newX = Math.floor(x / gridWidth) * gridWidth;
        double newY = Math.floor(y / gridHeight) * gridHeight;

        GraphicsContext gc = canvas.getGraphicsContext2D();

//        gc.setFill(Color.LIGHTGRAY);
//        gc.fillRect(newX, newY, gridWidth, gridHeight);
        gc.setFill(pickedColor);
        gc.setFont(new Font(gridWidth));
        gc.fillText(cross, newX, newY + gridHeight - 2);


        if (symStatus == 1) {               //vertical symmetry
            gc.fillText(cross, (500 - gridWidth - newX), newY + gridHeight - 2);

            Cross c = new Cross((500 - gridWidth - newX), newY, pickedColor, gridWidth);
            ornament.removeIf(cr -> cr.getX() == (500 - newX) && cr.getY() == newY);
            ornament.add(c);
        }

        else if (symStatus == 2) {          //horizontal symmetry
            gc.fillText(cross, newX, (500 - gridWidth - newY)  + gridHeight - 2);

            Cross c = new Cross(newX, (500 - gridWidth - newY), pickedColor, gridWidth);
            ornament.removeIf(cr -> cr.getX() == newX && cr.getY() == (500 - newY) - newY);
            ornament.add(c);
        }

        Cross c = new Cross(newX, newY, pickedColor, gridWidth);
        ornament.removeIf(cr -> cr.getX() == newX && cr.getY() == newY);
        ornament.add(c);

        for(Cross cr : ornament){           //видалити після тестувань
            System.out.println(cr);
        }
    }

    public static void deleteCross(MouseEvent e, double gridWidth, double gridHeight) {
        double x = e.getX();
        double y = e.getY();

        double newX = Math.floor(x / gridWidth) * gridWidth;
        double newY = Math.floor(y / gridHeight) * gridHeight;

        ornament.removeIf((Cross cr) -> cr.getX() == newX && cr.getY() == newY);
    }

    public static void deleteAll() {
        ornament.clear();
    }

}