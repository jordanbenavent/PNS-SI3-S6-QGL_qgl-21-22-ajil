package fr.unice.polytech.si3.qgl.ajil.shipentities;

public class Sail extends Entity{

    private boolean openned;

    public Sail(){
        setType("sail");
    }
    public Sail(int x, int y, String type, boolean openned){
        super(x,y,type);
        this.openned=openned;
    }

    public boolean getOpenned(){
        return openned;
    }

    public void setOpenned(boolean openned) {
        this.openned = openned;
    }
}
