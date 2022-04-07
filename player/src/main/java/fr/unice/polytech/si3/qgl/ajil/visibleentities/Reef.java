package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

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
