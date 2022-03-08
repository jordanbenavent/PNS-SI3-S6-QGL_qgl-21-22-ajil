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

    private Goal goal;
    private Ship ship;
    private List<Sailor> sailors;
    private int shipCount;

    public Game(){}

    public Game(Goal goal, Ship ship, List<Sailor> sailors, int shipCount) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
    }

    /**
     * @return "l'objectif" de la partie, c'est à dire le mode jeu ainsi que la liste de checkpoints à parcourir
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Modifie "l'objectif" de la partie
     * @param goal
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
     * @param ship
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * @return la liste des marins
     */
    public List<Sailor> getSailors() {
        return sailors;
    }

    /**
     * Modifie la liste des marins
     * @param sailors
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
     * @param shipCount
     */
    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }
}
