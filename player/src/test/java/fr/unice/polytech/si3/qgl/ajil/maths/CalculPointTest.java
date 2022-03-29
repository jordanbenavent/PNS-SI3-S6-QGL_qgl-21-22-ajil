package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.strategy.Strategy;
import fr.unice.polytech.si3.qgl.ajil.strategy.ValideCheckpoint;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.OtherShip;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Stream;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CalculPointTest {

    private Polygone polygone;
    private Rectangle rectangle;
    private Circle circle;

    @BeforeEach
    void setUp() {
        Point[] pointpolygone = {new Point(1,2), new Point(0,1), new Point(2,1)};
        polygone = new Polygone("circle",Math.PI/4, pointpolygone);
        rectangle = new Rectangle("rectangle", 2,2,0);
        circle = new Circle("circle", 1);
    }

    @Test
    void calculExtremityPointPolygone(){
        ArrayList<Point> points = CalculPoints.calculExtremityPoints(polygone, new Position(4,4,Math.PI/4));
        Assertions.assertEquals(new Point(2,5), points.get(0));
        Assertions.assertEquals(new Point(3,4), points.get(1));
        Assertions.assertEquals(new Point(3,6), points.get(2));
    }

    @Test
    void calculExtremityPointRectangle(){
        ArrayList<Point> point = CalculPoints.calculExtremityPoints(rectangle, new Position(2, 2, 0));
        ArrayList<Point> point3 = CalculPoints.calculExtremityPoints(circle, new Position(2, 2, 0));
        System.out.println(point3);
        Assertions.assertEquals(new Point(3, 3), point.get(0));
        Assertions.assertEquals(new Point(1, 3), point.get(1));
        Assertions.assertEquals(new Point(3, 1), point.get(2));
        Assertions.assertEquals(new Point(1, 1), point.get(3));
        rectangle = new Rectangle("rectangle", 2, 2, -3.35);
        ArrayList<Point> point2 = CalculPoints.calculExtremityPoints(rectangle, new Position(9, 3, 1.04));
        Assertions.assertEquals(7.5,point2.get(0).getX(), 0.3);
        Assertions.assertEquals(3,point2.get(0).getY(), 0.3);
        Assertions.assertEquals(9,point2.get(1).getX(), 0.3);
        Assertions.assertEquals(1.5,point2.get(1).getY(), 0.3);
        Assertions.assertEquals(9,point2.get(2).getX(), 0.3);
        Assertions.assertEquals(4.5,point2.get(2).getY(), 0.3);
        Assertions.assertEquals(10.5,point2.get(3).getX(), 0.3);
        Assertions.assertEquals(3,point2.get(3).getY(), 0.3);
    }

    @Test
    void entitiesToPolygoneEntitiesTest(){
        Point[] pointpolygone = {new Point(1,2), new Point(0,1), new Point(2,1)};
        polygone = new Polygone("circle",-2.94961, pointpolygone);
        rectangle = new Rectangle("rectangle", 1,2,2.67035);
        circle = new Circle("circle", 1);
        ArrayList<VisibleEntitie> list = new ArrayList<>();
        list.add(new Stream("stream",new Position(3,2,-0.558505),  rectangle, 100));
        list.add(new Reef("reef",new Position(3,3,0.3665), polygone));
        list.add(new OtherShip("ship",new Position(0,0,0),  circle, 100));
        ArrayList<VisibleEntitie> resultat = CalculPoints.entitiesToEntitiesPolygone(list);
        Assertions.assertTrue(resultat.get(0).getShape() instanceof Polygone);
        Assertions.assertTrue(resultat.get(1).getShape() instanceof Polygone);
        Assertions.assertTrue(resultat.get(2).getShape() instanceof Circle);
        Assertions.assertEquals(3.2, ((Polygone) resultat.get(1).getShape()).getVertices()[0].getX(), 0.05);
        Assertions.assertEquals(0.75, ((Polygone) resultat.get(1).getShape()).getVertices()[0].getY(), 0.05);
        Assertions.assertEquals(3.6, ((Polygone) resultat.get(0).getShape()).getVertices()[0].getX(), 0.05);
        Assertions.assertEquals(1.1, ((Polygone) resultat.get(0).getShape()).getVertices()[0].getY(), 0.05);
    }
}
