package fr.unice.polytech.si3.qgl.ajil.actions;

public abstract class Action {

    private int sailorId;
    private Actions type;

    Action (int sailorId, Actions type){
        this.sailorId=sailorId;
        this.type=type;
    }

    public int getSailorId() {
        return sailorId;
    }

    public Actions getType() {
        return type;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    public void setType(Actions type) {
        this.type = type;
    }
}
