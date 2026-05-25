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
    public static List <Cross> verticalOrnament = new ArrayList<>();
    public static List <Cross> horizontalOrnament = new ArrayList<>();

    public static void putCross(MouseEvent e, Canvas canvas, Color pickedColor, int symStatus, double gridWidth, double gridHeight) {
        double x = e.getX();
        double y = e.getY();

        double width = canvas.getWidth();
        double height = canvas.getHeight();

        double newX = Math.floor(x / gridWidth) * gridWidth;
        double newY = Math.floor(y / gridHeight) * gridHeight;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(pickedColor);
        gc.setFont(new Font(gridWidth + 4));
        gc.fillText(cross, newX, newY + gridHeight - 2);

        if (symStatus == 1) {               //vertical symmetry
            gc.fillText(cross, (width - gridWidth - newX), newY + gridHeight - 2);

            Cross c = new Cross((width - gridWidth - newX), newY, pickedColor, gridWidth);
            ornament.removeIf(cr -> cr.getX() == (width - gridWidth - newX) && cr.getY() == newY);
            ornament.add(c);
        }

        else if (symStatus == 2) {          //horizontal symmetry
            gc.fillText(cross, newX, (height - gridHeight - newY)  + gridHeight - 2);

            Cross c = new Cross(newX, (height - gridHeight - newY), pickedColor, gridWidth);
            ornament.removeIf(cr -> cr.getX() == newX && cr.getY() == (height - gridHeight - newY));
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
        horizontalOrnament.clear();
        verticalOrnament.clear();
    }

    public static void verticalDuplication(Canvas canvas , int spinnerValue) {
        double width = canvas.getWidth();
        double currentScale = width / spinnerValue;

        verticalOrnament.clear();

        for(Cross cros: ornament){
            long colIndex = Math.round(cros.getX() / cros.getOneCellScale());
            long rowIndex = Math.round(cros.getY() / cros.getOneCellScale());

            long mirroredColIndex = spinnerValue - 1 - colIndex;

            double newX = mirroredColIndex * currentScale;
            double newY = rowIndex * currentScale;

            Cross c = new Cross(newX, newY, cros.getColor(), currentScale);

            verticalOrnament.removeIf(cr -> cr.getX() == newX && cr.getY() == newY);
            verticalOrnament.add(c);
        }


        for (Cross newCross : verticalOrnament) {
            ornament.removeIf(oldCross -> oldCross.getX() == newCross.getX() && oldCross.getY() == newCross.getY());
        }
        ornament.addAll(verticalOrnament);
    }



    public static void horizontalDuplication(Canvas canvas, int spinnerValue) {
        double width = canvas.getWidth();
        double currentScale = width / spinnerValue;
        horizontalOrnament.clear();

        for(Cross cros: ornament){
            long colIndex = Math.round(cros.getX() / cros.getOneCellScale());
            long rowIndex = Math.round(cros.getY() / cros.getOneCellScale());

            long mirroredRowIndex = spinnerValue - 1 - rowIndex;

            double newX = colIndex * currentScale;
            double newY = mirroredRowIndex * currentScale;

            Cross c = new Cross(newX, newY, cros.getColor(), currentScale);

            horizontalOrnament.removeIf(cr -> cr.getX() == newX && cr.getY() == newY);
            horizontalOrnament.add(c);
        }

        for (Cross newCross : horizontalOrnament) {
            ornament.removeIf(oldCross -> oldCross.getX() == newCross.getX() && oldCross.getY() == newCross.getY());
        }
        ornament.addAll(horizontalOrnament);
    }

}