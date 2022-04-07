package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;

public class Segment {
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public Segment(Point a, Point b) {
        this.startX = a.getX();
        this.startY = a.getY();
        this.endX = b.getX();
        this.endY = b.getY();
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }
}
