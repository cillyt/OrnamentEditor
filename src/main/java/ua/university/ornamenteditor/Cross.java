package ua.university.ornamenteditor;

import javafx.scene.paint.Color;

public class Cross {
    Color color;
    double x;
    double y;
    Cross(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public String toString(){
        return "cross:" + color + " " + x + " " + y;
    }
}
