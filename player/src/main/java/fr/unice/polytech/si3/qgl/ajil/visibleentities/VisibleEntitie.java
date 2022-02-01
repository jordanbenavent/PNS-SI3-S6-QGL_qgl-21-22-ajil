package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Shape;
import fr.unice.polytech.si3.qgl.ajil.Ship;

public class VisibleEntitie {
    private String type;
    private Position position;
    private Shape shape;

    public VisibleEntitie(){}

    public VisibleEntitie(String type, Position position, Shape shape){
        this.type=type;
        this.position=position;
        this.shape=shape;
    }

    public String getType() {
        return type;
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

    public void setType (String type) {
        this.type=type;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
