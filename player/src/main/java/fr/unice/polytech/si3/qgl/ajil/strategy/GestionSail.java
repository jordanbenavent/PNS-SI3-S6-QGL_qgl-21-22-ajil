package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Sailor;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.Wind;
import fr.unice.polytech.si3.qgl.ajil.actions.LiftSail;
import fr.unice.polytech.si3.qgl.ajil.actions.LowerSail;

import java.util.ArrayList;
import java.util.List;

public class GestionSail {
    final static double RANGE = Math.PI / 2;
    protected StratData stratData;
    private boolean isLifted;

    public GestionSail(StratData stratData) {
        this.stratData = stratData;
    }

    /**
     * Actionne la voile si le bateau est dans la même direction que le vent
     *
     * @param ship le bateau
     * @param wind le vent
     */
    void putSail(Ship ship, Wind wind) {
        if (stratData.getSailorsManager() != null) {

            List<LiftSail> sailsLifts = new ArrayList<>();
            List<LowerSail> sailsLowers = new ArrayList<>();

            int currentCoxswain;
            for (Sailor s : stratData.getSailorsManager()) {
                currentCoxswain = s.getId();
                sailsLifts.add(new LiftSail(currentCoxswain));
                sailsLowers.add(new LowerSail(currentCoxswain));
            }

            stratData.actions.removeAll(sailsLifts);
            stratData.actions.removeAll(sailsLowers);

            if (isWindStraight(ship, wind)) {
                stratData.actions.addAll(sailsLifts);
                isLifted = true;
            } else {
                stratData.actions.addAll(sailsLowers);
                isLifted = false;
            }
        }
    }

    /**
     * Check if ship orientation === wind direction
     *
     * @param ship ship to get ship orientation
     * @param wind wind to get wind direction
     * @return true if alignement is ok
     */
    boolean isWindStraight(Ship ship, Wind wind) {
        double shipOrientation = ship.getPosition().getOrientation();
        double windOrientation = wind.getOrientation();
        final double result = simplifyAngle(shipOrientation, windOrientation);
        return Math.abs(result) <= RANGE;
    }

    /**
     * Make sure the angle is in -pi : pi range
     *
     * @param s shipOrientation angle in radian
     * @param w windOrientation angle in radian
     * @return a [-pi : pi] angle between vectors
     */
    double simplifyAngle(double s, double w) {
        double result = s - w;
        while (result < -Math.PI) result = result + 2 * Math.PI;
        while (result > Math.PI) result = result - 2 * Math.PI;
        return result % (2 * Math.PI);
    }

    /**
     * a small getter to tell if the sail is opened or closed
     *
     * @return sail state as boolean
     */
    boolean isSailLifted() {
        return this.isLifted;
    }
}
