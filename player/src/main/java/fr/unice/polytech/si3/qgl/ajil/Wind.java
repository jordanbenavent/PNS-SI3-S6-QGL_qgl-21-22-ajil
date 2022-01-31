package fr.unice.polytech.si3.qgl.ajil;

public class Wind {
    private double orientation;
    private double strength;

    public Wind(double orientation, double strength){
        this.orientation=orientation;
        this.strength=strength;
    }

    public double getOrientation() {
        return orientation;
    }

    public double getStrength() {
        return strength;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }
}
