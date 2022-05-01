package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CalculIntersectionTest {

    @Test
    void intersectionCircleCircleTest(){
        Circle circle1 = new Circle("circle",1);
        Position position1 = new Position(0,0,0);
        Circle circle2 = new Circle("circle",1);
        Position position2 = new Position(1,0,0);
        Assertions.assertTrue(CalculIntersection.intersection(circle1,position1,circle2,position2)); // 2 intersections
        Assertions.assertTrue(CalculIntersection.intersection(circle1,position1,circle2,new Position(0,2,0))); //1 intersection
        Assertions.assertFalse(CalculIntersection.intersection(circle1,position1,circle2,new Position(0,2.1,0))); // 0 intersection
        Assertions.assertTrue(CalculIntersection.intersection(circle1,position1,new Circle("circle",2),new Position(0,0,0))); // cercle l'un dans l'autre
    }

    @Test
    void intersectionCircleRectangleTest(){
        Circle circle1 = new Circle("circle",1);
        Position position1 = new Position(0,1,3.152);
        Rectangle rectangle = new Rectangle("rectangle",1,1,0);
        Position position2 = new Position(1,2,0);
        Assertions.assertTrue(CalculIntersection.intersection(circle1,position1,rectangle,position2)); // 2 intersections
        Assertions.assertTrue(CalculIntersection.intersection(circle1,position1,new Rectangle("rectangle", 2,2,0),new Position(0,3,0))); //1 intersection
        Assertions.assertFalse(CalculIntersection.intersection(circle1,position1,rectangle,new Position(2,0,0))); // 0 intersection
        Assertions.assertTrue(CalculIntersection.intersection(circle1,position1,new Rectangle("rectangle", 1.5,1.5,0),new Position(-2,1,Math.PI/4))); // cercle l'un dans l'autre
    }

    //Nouvelle version

    @Test
    void intersectionPointCircleCircle() {
        Circle circle = new Circle("circle", 2);
        Position position1 = new Position(0,2,0);
        Circle circle2 = new Circle("circle",3);
        Position position2 = new Position(4,1,0);
        System.out.println(CalculIntersection.intersectionPointCircleCircle(circle, position1, circle2, position2));
    }

    @Test
    void intersectionPointCircleDroite(){
        Circle circle = new Circle("circle", 2);
        Position position1 = new Position(0,2,0);
        Point point1 = new Point(4.2, 0.68);
        Point point2 = new Point(-3.46, 5.14);
        List<Point> result = CalculIntersection.intersectionPointDroiteCircle(circle, position1, point1, point2);
        Assertions.assertEquals( -1.02, result.get(0).getX(), 0.1);
        Assertions.assertEquals( 3.72, result.get(0).getY(), 0.1);
        Assertions.assertEquals( 2, result.get(1).getX(), 0.1);
        Assertions.assertEquals( 1.96, result.get(1).getY(), 0.1);

        //vertical

        position1 = new Position(0,2,0);
        point1 = new Point(-1, 0);
        point2 = new Point(-1, 5);
        result = CalculIntersection.intersectionPointDroiteCircle(circle, position1, point1, point2);
        Assertions.assertEquals( -1, result.get(0).getX(), 0.1);
        Assertions.assertEquals( 0.27, result.get(0).getY(), 0.1);
        Assertions.assertEquals( -1, result.get(1).getX(), 0.1);
        Assertions.assertEquals( 3.73, result.get(1).getY(), 0.1);

    }
    @Test
    void intersectionPointSegmentDroite() {
        Circle circle = new Circle("circle", 2);
        Position position1 = new Position(0, 2, 0);
        Point point1 = new Point(0.56, 2.94);
        Point point2 = new Point(3.52, -0.8);
        List<Point> result = CalculIntersection.intersectionCircleSegment(circle, position1, point1, point2);
        Assertions.assertEquals( 1.87, result.get(0).getX(), 0.01);
        Assertions.assertEquals( 1.28, result.get(0).getY(), 0.01);

        point1 = new Point(2, 4);
        point2 = new Point(2, -1);
        result = CalculIntersection.intersectionPointDroiteCircle(circle, position1, point1, point2);
        Assertions.assertEquals( 2, result.get(0).getX(), 0.1);
        Assertions.assertEquals( 2, result.get(0).getY(), 0.1);
    }

    @Test
    void intersectionPointCircleRectangleTest(){
        Circle circle1 = new Circle("circle",2);
        Position position1 = new Position(0,2,0.680678);
        Rectangle rectangle = new Rectangle("rectangle",2.27,4.86,2.25);
        Position position2 = new Position(2.03,1.58,0);
        List<Point> result = CalculIntersection.intersectionPointCircleRectangle(circle1,position1, rectangle, position2);
        Assertions.assertEquals(1.39, result.get(0).getX(), 0.01);
        Assertions.assertEquals(0.56, result.get(0).getY(), 0.01);
        Assertions.assertEquals(0.9, result.get(2).getX(), 0.01);
        Assertions.assertEquals(3.79, result.get(2).getY(), 0.01);



    }
}
