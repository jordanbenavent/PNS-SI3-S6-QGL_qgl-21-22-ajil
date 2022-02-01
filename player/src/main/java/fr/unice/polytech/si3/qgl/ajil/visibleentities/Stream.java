package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Shape;

public class Stream extends VisibleEntitie {
    private double strength;

    public Stream(){}
    public Stream(String type, Position position, Shape shape, double strength) {
        super(type, position, shape);
        this.strength=strength;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }
}
