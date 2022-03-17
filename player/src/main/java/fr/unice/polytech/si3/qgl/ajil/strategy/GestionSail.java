package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Cockpit;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.Wind;
import fr.unice.polytech.si3.qgl.ajil.actions.LiftSail;
import fr.unice.polytech.si3.qgl.ajil.actions.LowerSail;

import java.util.List;

public class GestionSail {
    final double RANGE = Math.PI / 2;
    private final List<String> LOGGER = Cockpit.LOGGER;
    protected StratData stratData;
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
        double s = ship.getPosition().getOrientation();
        double w = wind.getOrientation();

        if (stratData.getSailorsManager() != null) {
            int barreur = stratData.getSailorsManager().getId();
            LiftSail lift = new LiftSail(barreur);
            LowerSail lower = new LowerSail(barreur);
            stratData.actions.remove(lift);
            stratData.actions.remove(lower);

            double result = simplifyAngle(s, w);
            if (result <= RANGE && result >= -RANGE) {
                stratData.actions.add(lift);
                lifted = true;
                LOGGER.add("Voile UP");
            } else {
                stratData.actions.add(lower);
                lifted = false;
                LOGGER.add("Voile DOWN");
            }
        } else {
            LOGGER.add("Pas de Sailer Manager");
        }
    }

    private double simplifyAngle(double s, double w) {
        int timeOut = 9999;
        double result = s - w;
        while (result < -Math.PI && timeOut > 0) {
            result = result + 2 * Math.PI;
            timeOut--;
        }
        while (result > Math.PI && timeOut > 0) {
            result = result - 2 * Math.PI;
            timeOut--;
        }
        if (timeOut <= 0) {
            System.out.println("Err infinite while during simplifying angle");
        }
        return result;
    }

    public boolean isSailLifted() {
        return this.lifted;
    }
}
