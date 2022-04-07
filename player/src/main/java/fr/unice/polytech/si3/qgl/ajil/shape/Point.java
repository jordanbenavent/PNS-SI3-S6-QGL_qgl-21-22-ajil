package fr.unice.polytech.si3.qgl.ajil.shape;

import java.util.Objects;

/**
 * Classe Point
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Point {

    private double x;
    private double y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return la coordonnée x
     */
    public double getX() {
        return x;
    }

    /**
     * Modifie la coordonnée x
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return la coordonnée y
     */
    public double getY() {
        return y;
    }

    /**
     * Modifie la coordonnée y
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Ajoute les coordonnées de deux points
     *
     * @param other
     * @return un point qui est le résultat du calcul
     */
    public Point addPoint(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    /**
     * Calcule la distance entre deux points
     *
     * @param other
     * @return la distance
     */
    public double distance(Point other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     * @param o
     * @return true si les points sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    /**
     * @return un code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @return un string composé des coordonnées x et y
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ')';
    }
}
