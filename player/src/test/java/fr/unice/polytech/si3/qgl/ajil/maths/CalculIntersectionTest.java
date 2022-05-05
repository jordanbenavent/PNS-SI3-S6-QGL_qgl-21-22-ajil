package fr.unice.polytech.si3.qgl.ajil.maths;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
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

    @Test
    void intersectionPointCirclePolygoneTest(){
        Circle circle1 = new Circle("circle",2);
        Position position1 = new Position(0,2,0.680678);
        Point[] pointpolygone = {new Point(0, 1), new Point(1, 0), new Point(1, -1), new Point(0, -1), new Point(-1, 0)};
        Polygone polygone = new Polygone("polygon", 2.30383461, pointpolygone);
        Position position2 = new Position(2.,3, -0.71558499);
        List<Point> result = CalculIntersection.intersectionPointCirclePolygone(circle1,position1, polygone, position2);
        System.out.println(result);

        Assertions.assertEquals(1.41, result.get(0).getX(), 0.01);
        Assertions.assertEquals(3.41, result.get(0).getY(), 0.01);
        Assertions.assertEquals(2, result.get(3).getX(), 0.01);
        Assertions.assertEquals(2, result.get(3).getY(), 0.02);
        Assertions.assertEquals(1.54, result.get(1).getX(), 0.01);
        Assertions.assertEquals(3.27, result.get(1).getY(), 0.01);
        Assertions.assertEquals(1.73, result.get(2).getX(), 0.01);
        Assertions.assertEquals(3, result.get(2).getY(), 0.01);
    }


    @Test
    void intersectionPointSegmentSegment(){
        Point point1 = new Point(-3, 2);
        Point point2 = new Point(-3, 3);
        Point point3 = new Point(-2, 2);
        Point point4 = new Point(-2, 3);
        // deux segments verticaux de x différent
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).isEmpty());
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point3, point4, point1, point2).isEmpty());

        point3 = new Point(-3, 1);
        point4 = new Point(-3, 0);
        // deux segments verticaux de x identique sans chevauchement
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).isEmpty());
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point3, point4, point1, point2).isEmpty());

        point3 = new Point(-3, 4);
        point4 = new Point(-3, 2.46);
        // deux segments verticaux de x identique avec chevauchement
        Assertions.assertEquals(-3,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getX(),0.01);
        Assertions.assertEquals(3,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getY(),0.01);
        Assertions.assertEquals(-3,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getX(),0.01);
        Assertions.assertEquals(2.46,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getY(),0.01);

        point3 = new Point(-3.92, 2.26);
        point4 = new Point(-2.91, 3.64);
        // Un segment vertical et l'autre non sans croisement
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).isEmpty());
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point3, point4, point1, point2).isEmpty());

        point3 = new Point(-2.88084,2.4104);
        point4 = new Point(-1.71263,3.00079);
        // Un segment vertical et l'autre non sans croisement mais proche
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).isEmpty());
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point3, point4, point1, point2).isEmpty());

        point3 = new Point(-3.47123,2.54858);
        point4 = new Point(-1.83824,2.9003);
        //Un segment vertical et l'autre non vertical avec croisement
        Assertions.assertEquals(-3,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getX(),0.01);
        Assertions.assertEquals(2.65,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getY(),0.01);
        Assertions.assertEquals(-3,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getX(),0.01);
        Assertions.assertEquals(2.65,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getY(),0.01);

        point1 = new Point(-4,3);
        point2 = new Point(-2,2);
        point3 = new Point(-4,2);
        point4 = new Point(-2,1);
        //deux segments parallèles non verticaux et sans croisements
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).isEmpty());
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point3, point4, point1, point2).isEmpty());

        point1 = new Point(-4,3);
        point2 = new Point(-2,2);
        point3 = new Point(-6,4);
        point4 = new Point(0,1);
        //deux segments parallèles non verticaux et avec croisements
        Assertions.assertEquals(-4,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getX(),0.01);
        Assertions.assertEquals(3,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getY(),0.01);
        Assertions.assertEquals(-2,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(1).getX(),0.01);
        Assertions.assertEquals(2,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(1).getY(),0.01);
        Assertions.assertEquals(-4,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getX(),0.01);
        Assertions.assertEquals(3,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getY(),0.01);
        Assertions.assertEquals(-2,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(1).getX(),0.01);
        Assertions.assertEquals(2,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(1).getY(),0.01);

        point1 = new Point(-7,7);
        point2 = new Point(-3,5);
        point3 = new Point(-5,6);
        point4 = new Point(-1,4);
        //deux segments parallèles non verticaux et avec croisements
        Assertions.assertEquals(-3,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getX(),0.01);
        Assertions.assertEquals(5,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getY(),0.01);
        Assertions.assertEquals(-5,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(1).getX(),0.01);
        Assertions.assertEquals(6,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(1).getY(),0.01);
        Assertions.assertEquals(-5,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getX(),0.01);
        Assertions.assertEquals(6,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getY(),0.01);
        Assertions.assertEquals(-3,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(1).getX(),0.01);
        Assertions.assertEquals(5,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(1).getY(),0.01);

        point1 = new Point(-2.5,3);
        point2 = new Point(-1.5,2);
        point3 = new Point(-2.5,2);
        point4 = new Point(-3,1.5);
        //deux segments parallèles non verticaux et non parallèle sans croisement
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).isEmpty());
        Assertions.assertTrue(CalculIntersection.intersectionPointSegmentSegment(point3, point4, point1, point2).isEmpty());

        point1 = new Point(-2.5,3);
        point2 = new Point(-1.5,2);
        point3 = new Point(-2.5,2);
        point4 = new Point(-1,2.5);
        //deux segments parallèles non verticaux et non parallèle avec croisement
        Assertions.assertEquals(-1.75,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getX(),0.01);
        Assertions.assertEquals(2.25,CalculIntersection.intersectionPointSegmentSegment(point1, point2, point3, point4).get(0).getY(),0.01);
        Assertions.assertEquals(-1.75,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getX(),0.01);
        Assertions.assertEquals(2.25,CalculIntersection.intersectionPointSegmentSegment(point3, point4,point1, point2).get(0).getY(),0.01);

    }



}
