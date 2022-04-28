package fr.unice.polytech.si3.qgl.ajil.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.ObstacleDetection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObstacleDetectionTest {

    @Test
    void findOriginTest(){
        ObstacleDetection obstacleDetection = new ObstacleDetection();
        Point positionShip = new Point(55,55);
        Point positionCheckpoint = new Point(100,100);
        Point point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN + positionShip.getX(), -ObstacleDetection.MARGIN + positionShip.getY()), point);
        positionCheckpoint = new Point(100,-100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN + positionShip.getX(), -obstacleDetection.MARGIN + positionCheckpoint.getY()), point);
        positionCheckpoint = new Point(-100,-100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN + positionCheckpoint.getX(), -obstacleDetection.MARGIN + positionCheckpoint.getY()), point);
        positionCheckpoint = new Point(-100,100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN - positionCheckpoint.getY(), -obstacleDetection.MARGIN + positionShip.getY()), point);
    }


}
