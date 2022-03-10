package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;

/**
 * Classe NextRound
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class NextRound {

    private Ship ship;
    private ArrayList<VisibleEntitie> visibleEntities;
    private Wind wind;

    public NextRound(){}

    public NextRound(Ship ship, Wind wind, ArrayList<VisibleEntitie> visibleEntities){
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    /**
     * @return la liste des entités visibles durant le tour de jeu
     */
    public ArrayList<VisibleEntitie> getVisibleEntities() {
        return visibleEntities;
    }

    /**
     * @return le bateau durant le tour jeu
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * @return le vent durant le tour de jeu
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Modifie le bateau durant le tour de jeu
     * @param ship bateay
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Modifie les entités visibles durant le tour de jeu
     * @param visibleEntities entités visibles
     */
    public void setVisibleEntities(ArrayList<VisibleEntitie> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    /**
     * Modifie le vent durant le tour de jeu
     * @param wind vent
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * Modifie la partie durant le tour de jeu
     * @param game jeu
     */
    public void updateGame(Game game){
        game.setShip(this.ship);
        game.setWind(this.wind);
    }
}
