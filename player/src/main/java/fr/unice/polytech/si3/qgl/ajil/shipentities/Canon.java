package fr.unice.polytech.si3.qgl.ajil.shipentities;

public class Canon extends Entity{

    private boolean loaded;
    private double angle;

    public Canon(){
        setType("canon");
    }

    public Canon(int x, int y, String type, boolean loaded, double angle){
        super(x,y, type);
        this.loaded=loaded;
        this.angle=angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean getLoaded(){
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
