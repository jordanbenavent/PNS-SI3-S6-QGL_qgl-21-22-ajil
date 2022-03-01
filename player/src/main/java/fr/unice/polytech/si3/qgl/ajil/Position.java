package fr.unice.polytech.si3.qgl.ajil;

/**
 * Classe Position
 * Elle représente la position d'un objet sur la carte avec ses coordonnées ainsi que son orientation
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Position {

    private double x;
    private double y;
    private double orientation;

    public Position(){} // Pour le mapper

    public Position(double x, double y, double orientation){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    /**
     * @return la coordonnée x de la position de l'objet
     */
    public double getX() {
        return x;
    }

    /**
     * @return la coordonnée y de la position de l'objet
     */
    public double getY() {
        return y;
    }

    /**
     * @return l'orientation de la position de l'objet
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * Modifie l'orientation de la position de l'objet
     * @param orientation
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * Modifie la coordonnée x de la position de l'objet
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Modifie la coordonnée y de la position de l'objet
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return un string composé des coordonnées x et y ainsi que l'orientation
     */
    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                '}';
    }
}
