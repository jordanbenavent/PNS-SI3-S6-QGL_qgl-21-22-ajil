package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
