package fr.unice.polytech.si3.qgl.ajil.actions;

/**
 * Classe fille d'Action représentant l'action d'utiliser la vigie
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class UseWatch extends Action{
    public UseWatch(int sailorId) {
        super(sailorId, Actions.USE_WATCH);
    }
}
