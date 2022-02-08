package fr.unice.polytech.si3.qgl.ajil.shipentities;

public class OarEntity extends Entity {

    public OarEntity(){
        setType("oar");
    }
    public OarEntity(int x, int y,String type){
        super(x,y, type);
    }
}
