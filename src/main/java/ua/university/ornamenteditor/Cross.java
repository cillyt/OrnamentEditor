package ua.university.ornamenteditor;

import javafx.scene.paint.Color;

public class Cross {
    Color color;
    double cellX;
    double cellY;
    double oneCell;
    Cross(double cellX, double cellY, Color color, double oneCell) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.color = color;
        this.oneCell = oneCell;
    }
    public String toString(){
        return "cross:" + color + " " + cellX + " " + cellY + " " + oneCell ;
    }

    public double getX() {
        return cellX;
    }

    public double getY() {
        return cellY;
    }

    public Color getColor() {
        return color;
    }

    public double getOneCellScale() {
        return oneCell;
    }

    public void setX(double x) { this.cellX = x; }
    public void setY(double y) { this.cellY = y; }
    public void setOneCellScale(double oneCellScale) { this.oneCell = oneCellScale; }
}
