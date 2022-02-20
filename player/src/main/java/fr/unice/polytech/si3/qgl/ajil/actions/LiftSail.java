package fr.unice.polytech.si3.qgl.ajil.actions;

/**
 * Classe fille d'Action représentant l'action de lever la voile
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class LiftSail extends Action {
    public LiftSail(int sailorId) {
        super(sailorId, Actions.LIFT_SAIL);
    }
}
