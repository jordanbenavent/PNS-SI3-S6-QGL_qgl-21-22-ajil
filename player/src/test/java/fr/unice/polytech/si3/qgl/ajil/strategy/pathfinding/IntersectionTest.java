package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IntersectionTest {


        @BeforeEach
        void setUp() {

        }

        @Test
        void segmentIntersectionTest() {
                Segment s1 = new Segment(-1.14, 3.39, -1.14, 4.39);
                Segment s2 = new Segment(0.48, 1.15, 4, 2.35);
                assertNull(Intersection.segmentIntersection(s1, s2));
        }


        // collinear case
        @Test
        void segmentIntersectionTestCollinear() {
                Segment s1 = new Segment(1, 1, 4, 4);
                Segment s2 = new Segment(4, 4, 1, 1);
                assertNull(Intersection.segmentIntersection(s1, s2));
        }

        // intersection
        @Test
        void segmentIntersectionTestIntersectNormalCase() {
                Segment s1 = new Segment(1, 1, 4, 3);
                Segment s2 = new Segment(2, 1, 4, 4);
                Point res = new Point(2.8, 2.2);
                assertEquals(res, Intersection.segmentIntersection(s1, s2));
        }

}
