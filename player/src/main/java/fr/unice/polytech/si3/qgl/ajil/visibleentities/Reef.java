package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

public class Reef extends VisibleEntitie{

    public Reef(){
        setType("reef");
    }
    public Reef(String type, Position position, Shape shape) {
        super(type, position, shape, VisibleEntities.REEF);
    }
}
