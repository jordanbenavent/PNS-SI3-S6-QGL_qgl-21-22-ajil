package fr.unice.polytech.si3.qgl.ajil.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.ObstacleDetection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObstacleDetectionTest {
    private ObstacleDetection obstacleDetection;

    @BeforeEach
    void setUp() {
        obstacleDetection = new ObstacleDetection();
    }

    @Test
    void findOriginTest() {
        Point positionShip = new Point(55, 55);
        Point positionCheckpoint = new Point(100, 100);
        Point point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN + positionShip.getX(), -ObstacleDetection.MARGIN + positionShip.getY()), point);
        positionCheckpoint = new Point(100, -100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN + positionShip.getX(), -ObstacleDetection.MARGIN + positionCheckpoint.getY()), point);
        positionCheckpoint = new Point(-100, -100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN + positionCheckpoint.getX(), -ObstacleDetection.MARGIN + positionCheckpoint.getY()), point);
        positionCheckpoint = new Point(-100, 100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-ObstacleDetection.MARGIN - positionCheckpoint.getY(), -ObstacleDetection.MARGIN + positionShip.getY()), point);
    }


    @Test
    void obstacleToPointTest() {
        Circle circle = new Circle("circle", 1);
        Point[] points = obstacleDetection.rectangleToPoints(circle, new Position(0, 0, 0));
        float r45 = (float) ((circle.getRadius() + ObstacleDetection.MARGIN_CIRCLE) * 0.7);
        Assertions.assertEquals(-61, points[0].getX());
        Assertions.assertEquals(0, points[0].getY());
        Assertions.assertTrue(points[1].getX() > -r45 - 0.1);
        Assertions.assertTrue(points[1].getX() < -r45 + 0.1);
        Assertions.assertTrue(points[1].getY() > -r45 - 0.1);
        Assertions.assertTrue(points[1].getY() < -r45 + 0.1);
        Assertions.assertEquals(0, points[2].getX());
        Assertions.assertEquals(-61, points[2].getY());
        Assertions.assertTrue(points[3].getX() < r45 + 0.1);
        Assertions.assertTrue(points[3].getX() > r45 - 0.1);
        Assertions.assertTrue(points[3].getY() > -r45 - 0.1);
        Assertions.assertTrue(points[3].getY() < -r45 + 0.1);
        Assertions.assertEquals(61, points[4].getX());
        Assertions.assertEquals(0, points[4].getY());
        Assertions.assertTrue(points[5].getX() < r45 + 0.1);
        Assertions.assertTrue(points[5].getX() > r45 - 0.1);
        Assertions.assertTrue(points[5].getY() < r45 + 0.1);
        Assertions.assertTrue(points[5].getY() > r45 - 0.1);
        Assertions.assertEquals(0, points[6].getX());
        Assertions.assertEquals(61, points[6].getY());
        Assertions.assertTrue(points[7].getX() > -r45 - 0.1);
        Assertions.assertTrue(points[7].getX() < -r45 + 0.1);
        Assertions.assertTrue(points[7].getY() < r45 + 0.1);
        Assertions.assertTrue(points[7].getY() > r45 - 0.1);
    }
}
