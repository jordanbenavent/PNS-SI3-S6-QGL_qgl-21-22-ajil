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
        this.ship=ship;
        this.wind=wind;
        this.visibleEntities=visibleEntities;
    }

    public ArrayList<VisibleEntitie> getVisibleEntities() {
        return visibleEntities;
    }

    public Ship getShip() {
        return ship;
    }

    public Wind getWind() {
        return wind;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setVisibleEntities(ArrayList<VisibleEntitie> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void updateGame(Game game){
        game.setShip(this.ship);
    }
}
