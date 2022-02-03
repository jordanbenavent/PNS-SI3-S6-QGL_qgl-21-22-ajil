package fr.unice.polytech.si3.qgl.ajil.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rectangle extends Shape{

    private double width;
    private double height;
    private double orientation;

    public Rectangle(){
        setType("rectangle");
    }

    public Rectangle(String type, double width, double height, double orientation){
        super(type);
        this.height=height;
        this.width=width;
        this.orientation=orientation;
    }

    public double getOrientation() {
        return orientation;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return super.toString()+ "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                '}';
    }
}
