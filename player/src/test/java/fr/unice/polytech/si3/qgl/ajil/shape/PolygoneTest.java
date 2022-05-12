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
        Assertions.assertTrue(poly1.equals(poly2));
        Assertions.assertEquals(-33521758, poly1.hashCode());
        Assertions.assertEquals(-33521758, poly2.hashCode());
    }
}