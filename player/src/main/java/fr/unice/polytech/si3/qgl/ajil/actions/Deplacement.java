package fr.unice.polytech.si3.qgl.ajil.actions;

/**
 * Classe fille d'Action représentant l'action du déplacement du bateau
 * On l'utilise pour pouvoir prévoir où sera le bateau le tour suivant
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Deplacement {

    private double vitesse = 165.0;
    private double angle = 0.0;

    public Deplacement(){
    }

    public Deplacement(double vitesse, double angle){
        this.vitesse = vitesse;
        this.angle = angle;
    }

    /**
     * @return la vitesse du déplacement
     */
    public double getSpeed() {
        return vitesse;
    }

    /**
     * Modifie la vitesse du déplacement
     * @param vitesse setter to set Ship speed
     */
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * @return l'angle du déplacement
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Modifie l'angle du déplacement
     * @param angle setter to set ship angle
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * @return un string contenant la vitesse et l'angle du déplacement
     */
    public String toString(){
        return "" + this.vitesse + ", " + this.angle;
    }
}
