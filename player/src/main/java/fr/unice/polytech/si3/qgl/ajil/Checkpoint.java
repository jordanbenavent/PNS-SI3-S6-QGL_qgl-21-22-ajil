package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

/**
 * Classe Checkpoint représentant un checkpoint que le bateau doit atteindre pour réussir la course
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Checkpoint {

    private Position position;
    private Shape shape;

    public Checkpoint(){}

    public Checkpoint(Position position, Shape shape){
        this.position = position;
        this.shape = shape;
    }

    /**
     * @return la position du checkpoint
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return la forme du checkpoint
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Modifie la position du checkpoint
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Modifie la forme du checkpoint
     * @param shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * @return un string composé de la forme et de la position du checkpoint
     */
    @Override
    public String toString() {
        return "Checkpoint{" +
                "position=" + position +
                ", shape=" + shape +
                '}';
    }
}
