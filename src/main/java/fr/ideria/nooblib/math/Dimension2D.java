package fr.ideria.nooblib.math;

import fr.ideria.nooblib.data.json.Json;

public class Dimension2D {
    private double width, height;

    public Dimension2D(double width, double height){
        this.width = width;
        this.height = height;
    }

    public static Dimension2D fromJson(Json json){
        double width = json.getDouble("width");
        double height = json.getDouble("height");

        return new Dimension2D(width, height);
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }
}