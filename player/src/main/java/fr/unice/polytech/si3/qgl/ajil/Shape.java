package fr.unice.polytech.si3.qgl.ajil;

public class Shape {
    private String type;
    private double Radius;

    public Shape(){}
    public Shape(String type, double Radius){
        this.type = type;
        this.Radius = Radius;
    }

    public String getType() {
        return type;
    }

    public double getRadius() {
        return Radius;
    }

    void setType(String type) {
        this.type = type;
    }

    void setRadius(int radius) {
        Radius = radius;
    }
}
