package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.maths.Intersection;
import fr.unice.polytech.si3.qgl.ajil.maths.Segment;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IntersectionTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void segmentIntersectionTest() {
        Point a = new Point(-1.14, 3.39);
        Point b = new Point(-1.14, 4.39);
        Point c = new Point(0.48, 1.15);
        Point d = new Point(4, 2.35);
        Segment ab = new Segment(a, b);
        Segment ac = new Segment(c, d);
        assertNull(Intersection.segmentIntersection(ab, ac));
    }


    // collinear case
    @Test
    void segmentIntersectionTestCollinear() {
        Point a = new Point(1, 1);
        Point b = new Point(4, 4);
        Point c = new Point(4, 4);
        Point d = new Point(1, 1);
        Segment s1 = new Segment(a, b);
        Segment s2 = new Segment(c, d);
        assertNull(Intersection.segmentIntersection(s1, s2));
    }

    // intersection
    @Test
    void segmentIntersectionTestIntersectNormalCase() {
        Point a = new Point(1, 1);
        Point b = new Point(4, 3);
        Point c = new Point(2, 1);
        Point d = new Point(4, 4);
        Segment s1 = new Segment(a, b);
        Segment s2 = new Segment(c, d);
        Point res = new Point(2.8, 2.2);
        assertEquals(res, Intersection.segmentIntersection(s1, s2));
    }

}
