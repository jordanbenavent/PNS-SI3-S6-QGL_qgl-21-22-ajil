package fr.unice.polytech.si3.qgl.ajil.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Checkpoint;
import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStarDeployment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AStarDeploymentTest {

    @Test
    void positionToCheckpointTest() {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(1, 1, 0));
        positions.add(new Position(1, 8, 1));
        positions.add(new Position(7, 6, 2));
        positions.add(new Position(12, 5, 3));
        AStarDeployment aStar = new AStarDeployment(new Game(), 100);
        List<Checkpoint> checkpoints = aStar.convertPositionToCheckpoint(positions);
        Assertions.assertEquals(new Checkpoint(new Position(50 + aStar.getSizeCell()/2 + aStar.getSizeCell()/20 ,50+aStar.getSizeCell()/2 + aStar.getSizeCell()/20,0), new Circle("circle", aStar.getSizeCell()/20)), checkpoints.get(0));
        Assertions.assertEquals(new Checkpoint(new Position(100*7 + aStar.getSizeCell()/20 ,100*6 + aStar.getSizeCell()/20 ,0), new Circle("circle", aStar.getSizeCell()/20)), checkpoints.get(1));
    }
}
