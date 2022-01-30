package fr.unice.polytech.si3.qgl.ajil;

public class Position {
    private double x;
    private double y;
    private double orientation;

    public Position(){} // Pour le mapper

    public Position(double x, double y, double orientation){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getOrientation() {
        return orientation;
    }

    void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    void setX(double x) {
        this.x = x;
    }

    void setY(double y) {
        this.y = y;
    }
}
