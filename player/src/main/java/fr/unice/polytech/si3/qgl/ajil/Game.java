package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Stream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe Game représentant une partie
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Game {

    private Wind wind;
    private Goal goal;
    private Ship ship;
    private List<Sailor> sailors;
    private int shipCount;
    private Set<Reef> reefs;
    private Set<Reef> fakeReefs;
    private Set<Stream> streams;

    public Game() {
    }

    public Game(Goal goal, Ship ship, List<Sailor> sailors, int shipCount, Wind wind) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
        this.wind = wind;
        this.reefs = new HashSet<>();
        this.fakeReefs = new HashSet<>();
        this.streams = new HashSet<>();
    }

    /**
     * @return "l'objectif" de la partie, c'est à dire le mode jeu ainsi que la liste de checkpoints à parcourir
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Modifie "l'objectif" de la partie
     *
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
     *
     * @param ship ship
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Récup le vent
     *
     * @return objet de type vent
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Modifie le vent
     *
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
     *
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
     *
     * @param shipCount nb bateaux
     */
    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    /**
     * @return les faux récifs (les courants considérés comme des checkpoints)
     */
    public Set<Reef> getFakeReefs() {
        return new HashSet<>(fakeReefs);
    }

    /**
     * Modifie le set de faux récifs
     * @param fakeReefs
     */
    public void setFakeReefs(Set<Reef> fakeReefs) {
        this.fakeReefs = fakeReefs;
    }

    /**
     * @return les récifs
     */
    public Set<Reef> getReefs() {
        return new HashSet<>(reefs);
    }

    /**
     * Modifie le set de récifs
     * @param reefs
     */
    public void setReefs(Set<Reef> reefs) {
        this.reefs = reefs;
    }

    /**
     * @return les courants
     */
    public Set<Stream> getStreams(){
        return new HashSet<>(streams);
    }

    /**
     * Modifie les courants
     * @param streams
     */
    public void setStreams(Set<Stream> streams){
        this.streams = streams;
    }

}
