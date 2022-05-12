package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Checkpoint;
import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculIntersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValideCheckpoint {

    private List<Checkpoint> fakeCheckpoint = new ArrayList<>();
    protected final Game jeu;

    public ValideCheckpoint(Game jeu) {
        this.jeu = jeu;
    }

    public List<Checkpoint> getFakeCheckpoint() {
        return fakeCheckpoint;
    }

    public void setFakeCheckpoint(List<Checkpoint> fakeCheckpoint) {
        this.fakeCheckpoint = fakeCheckpoint;
    }

    /**
     * If checkpoints is not empty, return next checkpoint
     *
     * @param checkpoints List of the checkpoints
     * @return the next Checkpoint
     */
    public Checkpoint nextCheckpointTarget(List<Checkpoint> checkpoints) {
        Ship ship = jeu.getShip();
        List<Checkpoint> realOrFalse = realOrFakeCheckpoint(checkpoints);
        if (realOrFalse == null || realOrFalse.isEmpty()) return null;
        Checkpoint checkpointCurrent = realOrFalse.get(0);
        if (fakeCheckpoint != null && fakeCheckpoint.contains(checkpointCurrent)) {
            realOrFalse.remove(checkpointCurrent);
            realOrFalse = realOrFakeCheckpoint(checkpoints);
            if (realOrFalse == null || realOrFalse.isEmpty()) return null;
            checkpointCurrent = realOrFalse.get(0);
        }
        while (isShipInCheckpoint(ship, checkpointCurrent)) {
            realOrFalse.remove(checkpointCurrent);
            realOrFalse = realOrFakeCheckpoint(checkpoints);
            if (realOrFalse == null || realOrFalse.isEmpty()) return null;
            checkpointCurrent = realOrFalse.get(0);
        }
        return checkpointCurrent;
    }

    public void checkRealCheckpoint(List<Checkpoint> checkpoints, Ship ship) {
        if (checkpoints.isEmpty()) return;
        Checkpoint checkpointCurrent = checkpoints.get(0);
        if (checkpointCurrent == null) return;
        while (isShipInCheckpoint(ship, checkpointCurrent)) {
            fakeCheckpoint.clear();
            checkpoints.remove(checkpointCurrent);
            if (checkpoints.isEmpty()) return;
            else checkpointCurrent = checkpoints.get(0);
            if (checkpointCurrent == null) return;
        }
    }

    /**
     * Check if the ship is inside the checkpoint
     *
     * @param ship              ship is needed for hitbox calculation
     * @param checkpointCurrent get the checkpoint
     * @return ship in checkpoint boolean
     */
    private boolean isShipInCheckpoint(Ship ship, Checkpoint checkpointCurrent) {
        return CalculIntersection.intersectionShapes(ship.getShape(), ship.getPosition(), checkpointCurrent.getShape(), checkpointCurrent.getPosition());
    }


    private List<Checkpoint> realOrFakeCheckpoint(List<Checkpoint> checkpoints) {
        if (fakeCheckpoint == null || fakeCheckpoint.isEmpty()) {
            if (checkpoints.isEmpty()) return Collections.emptyList();
            return checkpoints;
        } else {
            return fakeCheckpoint;
        }
    }

}