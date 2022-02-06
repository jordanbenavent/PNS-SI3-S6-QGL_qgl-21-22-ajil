package fr.unice.polytech.si3.qgl.ajil.shipentities;

public class Rudder extends Entity{

    public Rudder(){
        setType("rudder");
    }
    public Rudder( int x, int y, String type){
        super(x,y,type);
    }
}
