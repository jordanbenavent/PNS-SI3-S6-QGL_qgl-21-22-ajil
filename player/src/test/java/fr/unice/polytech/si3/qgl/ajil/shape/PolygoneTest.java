package fr.unice.polytech.si3.qgl.ajil.shape;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolygoneTest {

    @Test
    void testEquals() {
        Point[] points = new Point[]{new Point(0,0), new Point(1,1)};
        Polygone poly1 = new Polygone("polygon", 0, new Point[0]);
        poly1.setVertices(points);
        Polygone poly2 = new Polygone("polygon", 0, points);
        Polygone poly3 = new Polygone("polygon", 3, points);
        Circle circle1 = new Circle("circle", 1);
        Assertions.assertTrue(poly1.equals(poly2));
        Assertions.assertEquals(-33521758, poly1.hashCode());
        Assertions.assertEquals(-33521758, poly2.hashCode());
        Assertions.assertFalse(poly1.equals(poly3));
        Assertions.assertFalse(poly1.equals(circle1));
    }
}