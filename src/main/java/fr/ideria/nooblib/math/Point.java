package fr.ideria.nooblib.math;

import fr.ideria.nooblib.data.json.Json;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void translate(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

    public void translate(Vector2D vector){
        this.x += vector.getXVelocity();
        this.y += vector.getYVelocity();
    }

    public double distanceFrom(Point point){ return sqrt(pow(x - point.y, 2) + pow(y - point.getY(), 2)); }

    public static Point fromJson(Json json){
        double x = json.getDouble("x");
        double y = json.getDouble("y");

        return new Point(x, y);
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}