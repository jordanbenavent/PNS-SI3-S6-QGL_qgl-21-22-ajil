package fr.unice.polytech.si3.qgl.ajil;

/**
 * Classe Wind représentant le vent
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Wind {

    private double orientation;
    private double strength;

    public Wind() {
    }

    public Wind(double orientation, double strength) {
        this.orientation = orientation;
        this.strength = strength;
    }

    /**
     * @return l'orientation du vent
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * Modifie l'orientation du vent
     * @param orientation
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * @return la force du vent
     */
    public double getStrength() {
        return strength;
    }

    /**
     * Modifie la force du vent
     * @param strength
     */
    public void setStrength(double strength) {
        this.strength = strength;
    }
}
