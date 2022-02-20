package fr.unice.polytech.si3.qgl.ajil.actions;

public class Deplacement {
    private double vitesse;
    private double angle;

    public Deplacement(){

    }

    public Deplacement(double vitesse, double angle){
        this.vitesse = vitesse;
        this.angle = angle;
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public String toString(){
        return "" + this.vitesse + ", " + this.angle;
    }
}
