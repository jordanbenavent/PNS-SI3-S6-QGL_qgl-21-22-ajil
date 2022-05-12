package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Stream;
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
 */

public class NextRound {

    private Ship ship;
    private List<VisibleEntitie> visibleEntities;
    private Wind wind;


    public NextRound() {
    }

    public NextRound(Ship ship, Wind wind, List<VisibleEntitie> visibleEntities) {
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

    /**
     * Modifie les entités visibles durant le tour de jeu
     *
     * @param visibleEntities entités visibles
     */
    public void setVisibleEntities(List<VisibleEntitie> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    /**
     * @return le bateau durant le tour jeu
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Modifie le bateau durant le tour de jeu
     *
     * @param ship bateay
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * @return le vent durant le tour de jeu
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Modifie le vent durant le tour de jeu
     *
     * @param wind vent
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * Modifie la partie durant le tour de jeu
     *
     * @param game jeu
     */
    public void updateGame(Game game) {
        game.setShip(this.ship);
        game.setWind(this.wind);
        game.setReefs(searchReef());
        game.setStreams(searchStream());
        game.setFakeReefs(streamToReef(searchStream()));
    }

    /**
     * @return tous les récifs dans visibleEntities
     */
    public Set<Reef> searchReef() {
        Set<Reef> reefs = new HashSet<>();
        for (VisibleEntitie entities : visibleEntities) {
            if (entities.getType().equals("reef")) {
                reefs.add((Reef) entities);
            }
        }
        return reefs;
    }

    /**
     * @return tous les courants présents dans visibleEntities
     */
    public Set<Stream> searchStream() {
        Set<Stream> streams = new HashSet<>();
        for (VisibleEntitie entities : visibleEntities) {
            if (entities.getType().equals("stream")) {
                streams.add((Stream) entities);
            }
        }
        return streams;
    }

    /**
     * Les courants ayant une force supérieure ou égale à 82.5 et étant contre le bateau sont
     * désormais comptés comme étant des faux récifs.
     * @param streams
     * @return les nouveaux récifs
     */
    public Set<Reef> streamToReef(Set<Stream> streams){
        Set<Reef> fakeReefs = new HashSet<>();
        for(Stream stream: streams){
            if(stream.getStrength() >= 82.5 && faceAuCourant(ship, stream)) {
                Reef reef = new Reef("reef", stream.getPosition(), stream.getShape());
                fakeReefs.add(reef);
            }
        }
        return fakeReefs;
    }

    /**
     * Permet de savoir si le bateau fait face au courant ou non
     * @param ship
     * @param stream
     * @return true si le bateau est face au courant, false sinon
     */
    public boolean faceAuCourant(Ship ship, Stream stream) {
        final Vector vShip = new Vector(Math.cos(ship.getPosition().getOrientation()), Math.sin(ship.getPosition().getOrientation()));
        final Vector vStream = new Vector(Math.cos(stream.getPosition().getOrientation()), Math.sin(stream.getPosition().getOrientation()));
        final double angle = vStream.angleBetweenVectors(vShip);
        // Si l'orientation du bateau et du courant est la même alors on retourne false
        if (ship.getPosition().getOrientation() == stream.getPosition().getOrientation()) {
            return false;
        }
        final double angle_final = Math.PI - Math.abs(angle);
        return angle_final < Math.PI / 4;
    }
}
