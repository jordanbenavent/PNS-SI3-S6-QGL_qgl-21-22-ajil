package fr.unice.polytech.si3.qgl.ajil.shape;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testEquals() {
        Rectangle rectangle1 = new Rectangle("rectangle", 100,100,0);
        Rectangle rectangle2 = new Rectangle("rectangle", 100,100,0);
        Rectangle rectangle3 = new Rectangle("rectangle", 100,100,3);
        Circle circle3 = new Circle("circle", 3);
        Assertions.assertTrue(rectangle1.equals(rectangle2));
        Assertions.assertEquals(rectangle1.hashCode(), rectangle2.hashCode());
        Assertions.assertNotEquals(0, rectangle1.hashCode());
        Assertions.assertFalse(rectangle1.equals(rectangle3));
        Assertions.assertFalse(rectangle1.equals(circle3));
    }

    @Test
    void testEqualsCircle() {
        Circle circle1 = new Circle("circle", 1);
        Circle circle2 = new Circle("circle", 1);
        Circle circle3 = new Circle("circle", 3);
        Rectangle rectangle2 = new Rectangle("rectangle", 100,100,0);
        Assertions.assertEquals(circle1.hashCode(), circle2.hashCode());
        Assertions.assertNotEquals(0, circle1.hashCode());
        Assertions.assertFalse(circle1.equals(rectangle2));
        Assertions.assertTrue(circle1.equals(circle2));
        Assertions.assertFalse(circle1.equals(circle3));
    }

    @Test
    void testEqualsPoint() {
        Point point1 = new Point(1,1);
        Point point2 = new Point(1,1);
        Point point3 = new Point(3,3);
        Circle circle3 = new Circle("circle", 3);
        Assertions.assertTrue(point1.equals(point2));
        Assertions.assertFalse(point1.equals(point3));
        Assertions.assertFalse(point1.equals(circle3));
    }
}