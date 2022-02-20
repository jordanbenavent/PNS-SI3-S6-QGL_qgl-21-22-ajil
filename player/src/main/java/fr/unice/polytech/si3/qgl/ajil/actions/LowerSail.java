package fr.unice.polytech.si3.qgl.ajil.actions;

/**
 * Classe fille d'Action représentant l'action de baisser la voile
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class LowerSail extends Action {
    public LowerSail(int sailorId) {
        super(sailorId, Actions.LOWER_SAIL);
    }
}
