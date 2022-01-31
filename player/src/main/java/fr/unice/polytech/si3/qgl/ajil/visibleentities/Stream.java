package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Shape;

public class Stream extends VisibleEntitie {
    private double strength;

    Stream(VisibleEntities visibleEntitie, Position position, Shape shape, double strength) {
        super(visibleEntitie, position, shape);
        this.strength=strength;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }
}
