package fr.unice.polytech.si3.qgl.ajil;

public class ShipShape {

    private String type;
    private double width;
    private double height;
    private double orientation;

    public ShipShape(){}
    public ShipShape(String type, double width, double height, double orientation){
        this.height = height;
        this.width = width;
        this.orientation = orientation;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
}
