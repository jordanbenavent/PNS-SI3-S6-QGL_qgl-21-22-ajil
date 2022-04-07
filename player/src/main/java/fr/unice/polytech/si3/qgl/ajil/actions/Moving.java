package fr.unice.polytech.si3.qgl.ajil.actions;

/**
 * Classe fille d'Action représentant l'action de déplacement du marin
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Moving extends Action {

    private int xdistance;
    private int ydistance;

    public Moving(int sailorId, int xdistance, int ydistance) {
        super(sailorId, Actions.MOVING);
        this.xdistance = xdistance;
        this.ydistance = ydistance;
    }

    /**
     * @return la distance parcourue en x
     */
    public int getXdistance() {
        return xdistance;
    }

    /**
     * Modifie la distance parcourue en x
     *
     * @param xdistance
     */
    public void setXdistance(int xdistance) {
        this.xdistance = xdistance;
    }

    /**
     * @return la distance parcourue en y
     */
    public int getYdistance() {
        return ydistance;
    }

    /**
     * Modifie la distance parcourue en y
     *
     * @param ydistance
     */
    public void setYdistance(int ydistance) {
        this.ydistance = ydistance;
    }
}
