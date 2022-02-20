package fr.unice.polytech.si3.qgl.ajil.shape;

import java.util.Objects;

public class Point {

    private double x;
    private double y;

    public Point(){}
    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * AJoute deux points
     * @param other
     * @return un point qui est le résultat du calcul
     */
    public Point addPoint(Point other) {
        return new Point(this.x+other.x, this.y+other.y);
    }

    /**
     * Calcule la distance entre deux point
     * @param other
     * @return la distance
     */
    public double distance(Point other) {
        return Math.sqrt((this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return  "("+x +
                "," + y +
                ')';
    }
}
