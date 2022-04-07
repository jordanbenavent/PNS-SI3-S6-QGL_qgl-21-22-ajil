package fr.unice.polytech.si3.qgl.ajil.shape;

import java.util.Arrays;
import java.util.Objects;

/**
 * Classe fille de Shape représentant un polygone
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Polygone extends Shape {

    private double orientation;
    private Point[] vertices;

    public Polygone() {
        setType("polygon");
    }

    public Polygone(String type, double orientation, Point[] vertices) {
        super(type);
        this.orientation = orientation;
        this.vertices = vertices;
    }

    /**
     * @return l'orientation du polygone
     */
    @Override
    public double getOrientation() {
        return orientation;
    }

    /**
     * Modifie l'orientation du polygone
     *
     * @param orientation
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * @return les sommets du polygone
     */
    public Point[] getVertices() {
        return vertices;
    }

    /**
     * Modifie les sommets du polygone
     *
     * @param vertices
     */
    public void setVertices(Point[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygone polygone = (Polygone) o;
        return Double.compare(polygone.orientation, orientation) == 0 && Arrays.equals(vertices, polygone.vertices);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(orientation);
        result = 31 * result + Arrays.hashCode(vertices);
        return result;
    }

    @Override
    public String toString() {
        return "Polygone{" + "orientation=" + orientation + ", vertices=" + Arrays.toString(vertices) + '}';
    }
}
