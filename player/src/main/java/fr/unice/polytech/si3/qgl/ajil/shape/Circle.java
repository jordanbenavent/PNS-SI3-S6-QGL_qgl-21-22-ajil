package fr.unice.polytech.si3.qgl.ajil.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;


public class Circle extends Shape{

    private double radius;

    public Circle(){
        setType("circle");
    }

    public Circle( String type, double radius){
        super(type);
        this.radius=radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return super.toString() + "Circle{" +
                "radius=" + radius +
                '}';
    }
}
