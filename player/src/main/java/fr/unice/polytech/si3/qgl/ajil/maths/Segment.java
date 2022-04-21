package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;

/**
 * Classe du package maths représentant un segment
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

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

    /**
     * @return la coordonnée X du point de départ du segment
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Modifie la coordonnée X du point de départ du segment
     * @param startX
     */
    public void setStartX(double startX) {
        this.startX = startX;
    }

    /**
     * @return la coordonnée Y du point de départ du segment
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Modifie la coordonnée Y du point de départ du segment
     * @param startY
     */
    public void setStartY(double startY) {
        this.startY = startY;
    }

    /**
     * @return la coordonnée X du point d'arrivée du segment
     */
    public double getEndX() {
        return endX;
    }

    /**
     * Modifie la coordonnée X du point d'arrivée du segment
     * @param endX
     */
    public void setEndX(double endX) {
        this.endX = endX;
    }

    /**
     * @return la coordonnée Y du point d'arrivée du segment
     */
    public double getEndY() {
        return endY;
    }

    /**
     * Modifie la coordonnée Y du point d'arrivée du segment
     * @param endY
     */
    public void setEndY(double endY) {
        this.endY = endY;
    }
}
