package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

/**
 * Classe fille de VisibleEntitie représentant un récif
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Reef extends VisibleEntitie {

    public Reef() {
        setType("reef");
    }

    public Reef(String type, Position position, Shape shape) {
        super(type, position, shape, VisibleEntities.REEF);
    }

    @Override
    public VisibleEntitie copy() {
        return new Reef(this.getType(), this.getPosition(), this.getShape());
    }
}
