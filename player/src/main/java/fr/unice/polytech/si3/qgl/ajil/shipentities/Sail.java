package fr.unice.polytech.si3.qgl.ajil.shipentities;

/**
 * Classe fille de Entity représentant une voile
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Sail extends Entity{

    private boolean openned;

    public Sail(){
        setType("sail");
    }

    public Sail(int x, int y, String type, boolean openned){
        super(x, y, type);
        this.openned = openned;
    }

    /**
     * @return true si la voile est ouverte, false sinon
     */
    public boolean getOpenned(){
        return openned;
    }

    /**
     * Modifie l'ouverture de la voile (la ferme ou l'ouvre)
     * @param openned
     */
    public void setOpenned(boolean openned) {
        this.openned = openned;
    }
}
