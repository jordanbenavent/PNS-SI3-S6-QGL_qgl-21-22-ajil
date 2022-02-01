package fr.unice.polytech.si3.qgl.ajil.actions;

public class Turn extends Action{

    private double rotation;

    public Turn(int sailorId, double rotation) {
        super(sailorId, Actions.TURN);
        this.rotation=rotation;
    }
}
