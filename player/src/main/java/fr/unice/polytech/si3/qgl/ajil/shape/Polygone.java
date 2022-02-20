package fr.unice.polytech.si3.qgl.ajil.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Polygone extends Shape{

    private double orientation;
    private Point[] vertices;

    public Polygone(){
        setType("polygone");
    }

    public Polygone(String type, double orientation, Point[] vertices){
        super(type);
        this.orientation=orientation;
        this.vertices=vertices;
    }

    public double getOrientation() {
        return orientation;
    }

    public Point[] getVertices() {
        return vertices;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public void setVertices(Point[] vertices) {
        this.vertices = vertices;
    }
}
