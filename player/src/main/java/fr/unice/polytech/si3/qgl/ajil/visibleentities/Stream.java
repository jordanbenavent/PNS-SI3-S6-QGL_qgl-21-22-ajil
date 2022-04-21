package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

/**
 * Classe fille de VisibleEntitie représentant un courant
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Stream extends VisibleEntitie {
    private double strength;

    public Stream() {
        setType("stream");
    }

    public Stream(String type, Position position, Shape shape, double strength) {
        super(type, position, shape, VisibleEntities.STREAM);
        this.strength = strength;
    }

    /**
     * @return la force du courant
     */
    public double getStrength() {
        return strength;
    }

    /**
     * Modifie la force du courant
     * @param strength
     */
    public void setStrength(double strength) {
        this.strength = strength;
    }

    /**
     * @return un string composé du string renvoyé par la méthode mère toString() + la force du courant
     */
    @Override
    public String toString() {
        return super.toString() +
                "strength=" + strength +
                '}';
    }

    @Override
    public VisibleEntitie copy() {
        return new Stream(this.getType(), this.getPosition(), this.getShape(), this.strength);
    }
}
