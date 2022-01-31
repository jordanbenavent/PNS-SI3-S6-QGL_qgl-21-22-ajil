package fr.unice.polytech.si3.qgl.ajil.actions;

public class Moving extends Action {

    private int xdistance;
    private int ydistance;

    public Moving(int sailorId, int xdistance, int ydistance) {
        super(sailorId, Actions.MOVING);
        this.xdistance = xdistance;
        this.ydistance = ydistance;
    }

    public int getXdistance() {
        return xdistance;
    }

    public int getYdistance() {
        return ydistance;
    }

    public void setXdistance(int xdistance) {
        this.xdistance = xdistance;
    }

    public void setYdistance(int ydistance) {
        this.ydistance = ydistance;
    }
}
