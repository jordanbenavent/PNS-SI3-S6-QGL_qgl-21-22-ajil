package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Cockpit;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.Wind;
import fr.unice.polytech.si3.qgl.ajil.actions.LiftSail;
import fr.unice.polytech.si3.qgl.ajil.actions.LowerSail;

import java.util.List;

public class GestionSail {
    private static final double RANGE = Math.PI / 2;
    private static final List<String> LOGGER = Cockpit.LOGGER;
    protected final StratData stratData;
    private boolean lifted;

    public GestionSail(StratData stratData) {
        this.stratData = stratData;
    }

    /**
     * Actionne la voile si le bateau est dans la même direction que le vent
     *
     * @param ship le bateau
     * @param wind le vent
     */
    public void putSail(Ship ship, Wind wind) {
        double shipOrientation = ship.getPosition().getOrientation();
        double windOrientation = wind.getOrientation();

        if (stratData.getSailorsManager() != null) {
            int barreur = stratData.getSailorsManager().getId();
            LiftSail lift = new LiftSail(barreur);
            LowerSail lower = new LowerSail(barreur);
            stratData.actions.remove(lift);
            stratData.actions.remove(lower);

            final double result = simplifyAngle(shipOrientation, windOrientation);
            if (result <= RANGE && result >= -RANGE) {
                stratData.actions.add(lift);
                lifted = true;
                //LOGGER.add("Voile UP");
            } else {
                stratData.actions.add(lower);
                lifted = false;
                //LOGGER.add("Voile DOWN");
            }
        }  //LOGGER.add("Pas de Sailer Manager");

    }

    /**
     * Make sure the angle is in -pi : pi range
     *
     * @param s shipOrientation angle in radian
     * @param w windOrientation angle in radian
     * @return a [-pi : pi] angle between vectors
     */
    private double simplifyAngle(double s, double w) {
        int timeOut = 99999;
        double result = s - w;
        while (result < -Math.PI && timeOut-- > 0) result = result + 2 * Math.PI;
        while (result > Math.PI && timeOut-- > 0) result = result - 2 * Math.PI;
        if (timeOut <= 0) System.out.println("Err infinite while during simplifying angle");
        return result;
    }

    /**
     * a small getter to tell if the sail is opened or closed
     *
     * @return sail state as boolean
     */
    public boolean isSailLifted() {
        return this.lifted;
    }
}
