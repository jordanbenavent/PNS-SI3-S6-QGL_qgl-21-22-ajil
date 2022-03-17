package fr.unice.polytech.si3.qgl.ajil.shipentities;

/**
 * Classe fille de Entity représentant une voile
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Sail extends Entity {
    private boolean opened;

    public Sail() {
        setType("sail");
    }

    public Sail(int x, int y, String type, boolean opened) {
        super(x, y, type);
        this.opened = opened;
    }

    /**
     * @return true si la voile est ouverte, false sinon
     */
    public boolean isSailOpened() {
        return opened;
    }

    /**
     * Modifie l'ouverture de la voile (la ferme ou l'ouvre)
     *
     * @param opened if sails is opened return true
     */
    public void setSail(boolean opened) {
        this.opened = opened;
    }
}
