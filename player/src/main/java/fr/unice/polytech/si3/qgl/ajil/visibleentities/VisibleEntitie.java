package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Shape;
import fr.unice.polytech.si3.qgl.ajil.Ship;

public abstract class VisibleEntitie {
    private VisibleEntities visibleEntitie;
    private Position position;
    private Shape shape;

    VisibleEntitie(VisibleEntities visibleEntitie, Position position, Shape shape){
        this.visibleEntitie=visibleEntitie;
        this.position=position;
        this.shape=shape;
    }

    public VisibleEntities getVisibleEntitie() {
        return visibleEntitie;
    }

    public Position getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setVisibleEntitie(VisibleEntities visibleEntitie) {
        this.visibleEntitie = visibleEntitie;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
