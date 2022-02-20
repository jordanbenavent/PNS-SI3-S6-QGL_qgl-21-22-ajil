package fr.unice.polytech.si3.qgl.ajil.shipentities;

/**
 * Classe fille de Entity représentant un canon
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Canon extends Entity{

    private boolean loaded;
    private double angle;

    public Canon(){
        setType("canon");
    }

    public Canon(int x, int y, String type, boolean loaded, double angle){
        super(x, y, type);
        this.loaded = loaded;
        this.angle = angle;
    }

    /**
     * @return l'angle de visée
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Modifie l'angle de visée
     * @param angle
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * @return true si le canon est chargé, false sinon
     */
    public boolean getLoaded(){
        return loaded;
    }

    /**
     * Modifie le boolean loaded qui signifie si le canon est chargé ou non
     * @param loaded
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
