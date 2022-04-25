package fr.unice.polytech.si3.qgl.ajil.strategy.evitement;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.Set;

public class DetecteCollision {


    //ArrayList d'entities ailleurs ou sinon un set
    final Set<VisibleEntitie> obstacles;
    final Ship bateau;


    DetecteCollision(Set<VisibleEntitie> visibleEntities, Ship ship) {
        obstacles = visibleEntities;
        bateau = ship;
    }

    //Position du bateau
    Position futurePosition(Position position, Deplacement deplacement) {
        Position res = new Position();
        double xDeplacement = deplacement.getVitesse() * Math.cos(position.getOrientation()+deplacement.getAngle());
        double yDeplacement = deplacement.getVitesse() * Math.sin(position.getOrientation()+deplacement.getAngle());

        res.setX(position.getX()+xDeplacement);
        res.setY(position.getY()+yDeplacement);

        return res;
    }









}
