package fr.unice.polytech.si3.qgl.ajil;
import java.util.List;

/**
 * Classe Game représentant une partie
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Game {

    private Wind wind;
    private Goal goal;
    private Ship ship;
    private List<Sailor> sailors;
    private int shipCount;

    public Game(){}

    public Game(Goal goal, Ship ship, List<Sailor> sailors, int shipCount, Wind wind) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
        this.wind = wind;
    }

    /**
     * @return "l'objectif" de la partie, c'est à dire le mode jeu ainsi que la liste de checkpoints à parcourir
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Modifie "l'objectif" de la partie
     * @param goal goal
     */
    void setGoal(Goal goal) {
        this.goal = goal;
    }

    /**
     * @return le bateau participant
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Modifie le bateau participant
     * @param ship ship
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Récup le vent
     * @return objet de type vent
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Modifie le vent
     * @param wind le vent
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * @return la liste des marins
     */
    public List<Sailor> getSailors() {
        return sailors;
    }

    /**
     * Modifie la liste des marins
     * @param sailors marins
     */
    public void setSailors(List<Sailor> sailors) {
        this.sailors = sailors;
    }

    /**
     * @return le nombre de bateaux participants
     */
    public int getShipCount() {
        return shipCount;
    }

    /**
     * Modifie le nombre de bateaux participants
     * @param shipCount nb bateaux
     */
    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }
}
