package fr.unice.polytech.si3.qgl.ajil.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculPoints;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.ObstacleDetection;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ObstacleDetectionTest {
    private ObstacleDetection obstacleDetection;

    @BeforeEach
    void setUp(){
        obstacleDetection = new ObstacleDetection();
    }

    @Test
    void findOriginTest(){
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


    @Test
    void obstacleToPointTest() {
        Circle circle = new Circle("circle",1);
        Point[] points = obstacleDetection.rectangleToPoints(circle, new Position(0,0,0));
        Assertions.assertEquals(-1, points[0].getX());
        Assertions.assertEquals(0, points[0].getY());
        Assertions.assertTrue(points[1].getX() > -0.71);
        Assertions.assertTrue(points[1].getX() < -0.69);
        Assertions.assertTrue(points[1].getY() > -0.71);
        Assertions.assertTrue(points[1].getY() < -0.69);
        Assertions.assertEquals(0, points[2].getX());
        Assertions.assertEquals(-1, points[2].getY());
        Assertions.assertTrue(points[3].getX() < 0.71);
        Assertions.assertTrue(points[3].getX() > 0.69);
        Assertions.assertTrue(points[3].getY() > -0.71);
        Assertions.assertTrue(points[3].getY() < -0.69);
        Assertions.assertEquals(1, points[4].getX());
        Assertions.assertEquals(0, points[4].getY());
        Assertions.assertTrue(points[5].getX() < 0.71);
        Assertions.assertTrue(points[5].getX() > 0.69);
        Assertions.assertTrue(points[5].getY() < 0.71);
        Assertions.assertTrue(points[5].getY() > 0.69);
        Assertions.assertEquals(0, points[6].getX());
        Assertions.assertEquals(1, points[6].getY());
        Assertions.assertTrue(points[7].getX() > -0.71);
        Assertions.assertTrue(points[7].getX() < -0.69);
        Assertions.assertTrue(points[7].getY() < 0.71);
        Assertions.assertTrue(points[7].getY() > 0.69);
    }
}
