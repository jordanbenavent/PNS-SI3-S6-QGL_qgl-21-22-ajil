package fr.unice.polytech.si3.qgl.ajil.actions;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

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
