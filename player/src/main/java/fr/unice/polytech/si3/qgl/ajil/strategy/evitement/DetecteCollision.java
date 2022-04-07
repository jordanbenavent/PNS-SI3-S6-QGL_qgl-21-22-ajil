package fr.unice.polytech.si3.qgl.ajil.strategy.evitement;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.Set;

public class DetecteCollision {


    //ArrayList d'entities ailleurs ou sinon un set
    Set<VisibleEntitie> obstacles;
    Ship bateau;


    DetecteCollision(Set<VisibleEntitie> visibleEntities, Ship ship) {
        obstacles = visibleEntities;
        bateau = ship;
    }

    //Position du bateau
    Position futurePosition(Position position, Deplacement deplacement) {
        Position res = new Position();
        final double orientation = position.getOrientation();
        final double vitesse = deplacement.getVitesse();
        final double angle = deplacement.getAngle();

        final double xDeplacement = vitesse * Math.cos(orientation + angle);
        final double yDeplacement = vitesse * Math.sin(orientation + angle);
        res.setX(position.getX() + xDeplacement);
        res.setY(position.getY() + yDeplacement);
        return res;
    }


}
