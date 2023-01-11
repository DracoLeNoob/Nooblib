package fr.ideria.nooblib.math;

import fr.ideria.nooblib.log.Log;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Vector2D {
    private final Point from;
    private final Point to;
    private final double xVelocity;
    private final double yVelocity;
    private final double length;
    private final double angle;

    public Vector2D(Point from, Point to){
        this.from = from;
        this.to = to;
        this.xVelocity = to.getX() - from.getX();
        this.yVelocity = to.getY() - from.getY();
        this.length = from.distanceFrom(to);
        this.angle = Math.atan(Math.abs(yVelocity) / Math.abs(xVelocity)) * (180 / Math.PI);
    }

    public Vector2D(Point from, double length, double angle){
        this.from = from;
        this.xVelocity = length * cos(angle);
        this.yVelocity = length * sin(angle);
        this.length = length;
        this.angle = angle;
        this.to = applyTo(from);
    }

    public Vector2D(double angle, double length){
        this.from = new Point(0, 0);
        this.length = length;
        this.angle = angle;

        double _ang = angle;
        int quarter = 0;
        while(_ang >= 90){
            _ang -= 90;
            quarter++;
        }

        this.xVelocity = length * cos(_ang);
        this.yVelocity = length * sin(_ang);

        Log.print(angle);
        Log.print(quarter);

        this.to = switch(quarter){
            case 0 -> new Point(xVelocity, yVelocity);
            case 1 -> new Point(xVelocity, -yVelocity);
            case 2 -> new Point(-xVelocity, -yVelocity);
            case 3 -> new Point(-xVelocity, yVelocity);
            default -> from;
        };
    }

    public Point applyTo(Point point){
        point.translate(xVelocity, yVelocity);
        return point;
    }

    public Point getFrom() { return from; }
    public Point getTo() { return to; }
    public double getXVelocity() { return xVelocity; }
    public double getYVelocity() { return yVelocity; }
    public double getLength() { return length; }
    public double getAngle() { return angle; }

    @Override
    public String toString() {
        return "Vector{" +
                "from=" + from +
                ", to=" + to +
                ", xVelocity=" + xVelocity +
                ", yVelocity=" + yVelocity +
                ", length=" + length +
                ", angle=" + angle +
                '}';
    }
}