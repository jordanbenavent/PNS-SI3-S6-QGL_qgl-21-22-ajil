package fr.unice.polytech.si3.qgl.ajil.actions;

/**
 * Classe fille d'Action représentant l'action de tourner le gouvernail
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Turn extends Action{

    private double rotation;

    public Turn(int sailorId, double rotation) {
        super(sailorId, Actions.TURN);
        this.rotation=rotation;
    }
}
