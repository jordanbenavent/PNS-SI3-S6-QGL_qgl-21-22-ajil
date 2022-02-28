package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;

/**
 * Classe Rudder représentant le gouvernail
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Rudder extends Entity {
    private double rotation = 0.0;

    public Rudder(double rotation) {
        this.rotation = rotation;
    }

    public Rudder() {
        super();
    }

    public double getRotation() {
        return rotation;
    }
}
