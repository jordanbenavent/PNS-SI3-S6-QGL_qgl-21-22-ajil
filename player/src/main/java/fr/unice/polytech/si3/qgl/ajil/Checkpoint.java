package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checkpoint that = (Checkpoint) o;
        return Objects.equals(position, that.position) && Objects.equals(shape, that.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, shape);
    }

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
