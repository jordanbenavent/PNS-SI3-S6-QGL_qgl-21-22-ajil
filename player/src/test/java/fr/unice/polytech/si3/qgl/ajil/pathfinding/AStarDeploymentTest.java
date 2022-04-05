package fr.unice.polytech.si3.qgl.ajil.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Checkpoint;
import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStarDeployment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AStarDeploymentTest {

    @Test
    void positionToCheckpointTest(){
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(new Position(1, 1,0));
        positions.add(new Position(1, 8,1));
        positions.add(new Position(7, 6, 2));
        positions.add(new Position(12, 5, 3));
        AStarDeployment aStar = new AStarDeployment(new Game(), 50);
        ArrayList<Checkpoint> checkpoints = aStar.convertPositionToCheckpoint(positions);
        //Assertions.assertEquals(new Checkpoint(new Position(75,75,0), new Circle("circle", aStar.getSizeCell()/2)), checkpoints.get(0));
        //Assertions.assertEquals(new Checkpoint(new Position(75,425,0), new Circle("circle", aStar.getSizeCell()/2)), checkpoints.get(1));
        //Assertions.assertEquals(new Checkpoint(new Position(375,325,0), new Circle("circle", aStar.getSizeCell()/2)), checkpoints.get(2));
        //Assertions.assertEquals(new Checkpoint(new Position(625,275,0), new Circle("circle", aStar.getSizeCell()/2)), checkpoints.get(3));
    }
}
