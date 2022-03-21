package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

public class Stream extends VisibleEntitie {
    private double strength;

    public Stream(){
        setType("stream");
    }
    public Stream(String type, Position position, Shape shape, double strength) {
        super(type, position, shape, VisibleEntities.STREAM);
        this.strength=strength;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return super.toString() + "Stream{" +
                "strength=" + strength +
                '}';
    }
}
