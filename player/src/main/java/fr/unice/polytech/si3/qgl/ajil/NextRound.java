package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<VisibleEntitie> visibleEntities;
    private Wind wind;
    public static final List<String> LOGGER = Cockpit.LOGGER;
    private static boolean newRecifAdded = false;


    public NextRound(){}

    public NextRound(Ship ship, Wind wind,List<VisibleEntitie> visibleEntities){
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    /**
     * @return la liste des entités visibles durant le tour de jeu
     */
    public List<VisibleEntitie> getVisibleEntities() {
        return visibleEntities;
    }

    public static boolean getNewRecifAdded(){
        return newRecifAdded;
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

    public boolean isNewRecifAdded() {return newRecifAdded;}

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
    public void setVisibleEntities(List<VisibleEntitie> visibleEntities) {
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
        game.setReefs(searchReef());
    }

    public Set<Reef> searchReef(){
        Set<Reef> reefs = new HashSet<>();
        LOGGER.add("size entities"+visibleEntities.size());

        this.newRecifAdded = false;
        for (VisibleEntitie entities : visibleEntities){
            if( entities.getType().equals("reef")){
                System.out.println("On a essaye d'ajouter un recif, peut etre il y etait deja");
                reefs.add((Reef)entities);
                this.newRecifAdded = true;
            }
        }
        return reefs;
    }
}
